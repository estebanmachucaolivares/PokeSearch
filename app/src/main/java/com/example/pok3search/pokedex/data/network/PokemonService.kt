package com.example.pok3search.pokedex.data.network

import android.util.Log
import com.example.pok3search.pokedex.data.network.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient:PokemonClient)  {

    suspend fun getAllPokemonWithRegion(): List<PokemonWithIdGroupByRegionResponse>{
        val generations = pokemonClient.getAllGenerations()

        val pokemonGroupByRegionList:MutableList<PokemonWithIdGroupByRegionResponse> = mutableListOf()

        for (generation in generations.results){
            val pokemonsGeneration = pokemonClient.getGeneration(generation.url)

            val pokemonGroupByRegion = PokemonWithIdGroupByRegionResponse(pokemonsGeneration.main_region.name.replaceFirstChar { it.uppercase() })

            // Iterar a través de los Pokémon y obtener el último número de la URL
            pokemonGroupByRegion.pokemonList = pokemonsGeneration.pokemon_species.map { pokemon ->
                val numeroPokemon = getPokemonIdForUrl(pokemon.url)
                PokemonWithIdResponse(numeroPokemon,pokemon.name)
            }.sortedBy{it.id}

            pokemonGroupByRegionList.add(pokemonGroupByRegion)

        }

        return pokemonGroupByRegionList
    }

    fun getPokemonIdForUrl(url: String): Int {
        // Dividir la URL en partes usando "/" como separador
        val urlParts = url.split("/")

        // El último elemento de la lista será el número que necesitas
        val lastElement = urlParts[urlParts.size-2]

        // Intentar convertir el último elemento a un entero y devolverlo
        return try {
            lastElement.toInt()
        } catch (e: NumberFormatException) {
            // Manejo de errores en caso de que el último elemento no sea un número válido
            -1 // Puedes elegir un valor predeterminado o lanzar una excepción aquí
        }
    }

    suspend fun getPokemonDetails(pokemonId: Int): DescriptionResponse{

        val pokemonDescriptions = pokemonClient.getPokemonDetails(pokemonId)

        val pokemonType = pokemonDescriptions.genera.find { it.language.name == "es" }?.genus ?:
        pokemonDescriptions.genera.find { it.language.name == "en" }?.genus ?: "Sin Información"

        val pokemonDescription = pokemonDescriptions.flavor_text_entries.find { it.language.name == "es" }?.flavor_text ?:
        pokemonDescriptions.flavor_text_entries.find { it.language.name == "en" }?.flavor_text ?:"Sin Información"

        return DescriptionResponse(pokemonId,pokemonType,pokemonDescription)
    }

    suspend fun getEvolutionChainForPokemon(pokemonId: Int): List<PokemonEvolutionResponse> = withContext(Dispatchers.IO) {
        try {
            val evolutionUrl = pokemonClient.getPokemonEvolutionChainUrl(pokemonId)
            val call = pokemonClient.getPokemonEvolutionChain(evolutionUrl.evolution_chain.url)
            val response = call.execute()

            if (!response.isSuccessful) {
                return@withContext emptyList()
            }

            val evolutionChain = response.body() ?: return@withContext emptyList()
            val evolutionNames = mutableListOf<PokemonEvolutionResponse>()

            fun collectEvolutions(evolution: Evolution, level: Int) {
                val pokemonId = getPokemonIdForUrl(evolution.species.url)
                evolutionNames.add(PokemonEvolutionResponse(PokemonWithIdResponse(pokemonId, evolution.species.name), level))

                for (subEvolution in evolution.evolves_to) {
                    collectEvolutions(subEvolution, level + 1)
                }
            }

            collectEvolutions(Evolution(evolutionChain.chain.evolves_to,evolutionChain.chain.species), 1)
            evolutionNames
        } catch (e: Exception) {
            emptyList()
        }
    }


    suspend fun getPokemonStats(pokemonId: Int) = pokemonClient.getPokemonStats(pokemonId)

    suspend fun getPokemonAbilities(pokemonId:Int):List<PokemonAbilityResultResponse>{
        val generations = pokemonClient.getPokemonAbilityUrls(pokemonId)

        val pokemonAbilityResult: MutableList<PokemonAbilityResultResponse> = mutableListOf()

        for(abilityUrl in generations.abilities ){
            val habilityList = pokemonClient.getPokemonAbility(abilityUrl.ability.url)

            val name = habilityList.names.find { it.language.name == "es" }?.name
            val description = habilityList.flavor_text_entries.find { it.language.name == "es" }?.flavor_text

            if(!name.isNullOrEmpty() && !description.isNullOrEmpty()){
                pokemonAbilityResult.add(PokemonAbilityResultResponse(pokemonId,name,description))
            }
        }

        return pokemonAbilityResult
    }


    suspend fun getPokemonTypes(pokemonId:Int):List<PokemonTypeResponse>{
        val typesUrl = pokemonClient.getPokemonTypesUrls(pokemonId)

        val pokemonTypesResult: MutableList<PokemonTypeResponse> = mutableListOf()

        for(typeUrl in typesUrl.types ){
            val types = pokemonClient.getPokemonTypes(typeUrl.type.url)

            val pokemonTypeName = types.names.find{ it.language.name == "es" }?.name ?: types.names.find{ it.language.name == "en" }?.name ?:""

            if(pokemonTypeName.isNotEmpty()){
                pokemonTypesResult.add(PokemonTypeResponse(pokemonId,pokemonTypeName))
            }
        }
        return pokemonTypesResult
    }

}