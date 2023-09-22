package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.data.network.response.PokemonListItemResponse
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdResponse
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdGroupByRegionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient:PokemonClient)  {

    suspend fun getAllPokemons(): List<PokemonListItemResponse>{
        return pokemonClient.getPokemonList(500).results
    }

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

    suspend fun getPokemonDetails(pokemonId: Int) = pokemonClient.getPokemonDetails(pokemonId)

    suspend fun getEvolutionChainForPokemon(pokemonUrl: String):List<PokemonWithIdResponse> = withContext(Dispatchers.IO){
        val call = pokemonClient.getEvolutionChain(pokemonUrl)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val evolutionChain = response.body()
                val evolutionNames = mutableListOf<PokemonWithIdResponse>()

                if (evolutionChain != null) {
                    val pokemonId = getPokemonIdForUrl(evolutionChain.chain.species.url)
                    evolutionNames.add(PokemonWithIdResponse(pokemonId, evolutionChain.chain.species.name))

                    if (evolutionChain.chain.evolves_to.isNotEmpty()) {
                        for (evolution in evolutionChain.chain.evolves_to) {
                            val pokemonId = getPokemonIdForUrl(evolution.species.url)
                            evolutionNames.add(PokemonWithIdResponse(pokemonId, evolution.species.name))
                            if (evolution.evolves_to.isNotEmpty()) {
                                val name = evolution.evolves_to[0].species.name
                                val pokemonId = getPokemonIdForUrl(evolution.evolves_to[0].species.url)
                                evolutionNames.add(PokemonWithIdResponse(pokemonId, name))
                            }
                        }
                    }
                }
                evolutionNames
            } else {
                // Manejar el caso en el que la solicitud no sea exitosa
                emptyList()
            }
        } catch (e: Exception) {
            // Manejar el caso en el que haya un error en la solicitud
            emptyList()
        }
    }

    suspend fun getPokemonStats(pokemonId: Int) = pokemonClient.getPokemonStats(pokemonId)

}