package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import com.example.pok3search.pokedex.data.network.response.PokemonWithId
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdGroupByRegion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient:PokemonClient)  {

    suspend fun getAllPokemons(): List<PokemonListItem>{
        return pokemonClient.getPokemonList(500).results
    }

    suspend fun getAllPokemonWithRegion(): List<PokemonWithIdGroupByRegion>{
        val generations = pokemonClient.getAllGenerations()

        val pokemonGroupByRegionList:MutableList<PokemonWithIdGroupByRegion> = mutableListOf()

        for (generation in generations.results){
            val pokemonsGeneration = pokemonClient.getGeneration(generation.url)

            val pokemonGroupByRegion = PokemonWithIdGroupByRegion(pokemonsGeneration.main_region.name.replaceFirstChar { it.uppercase() })

            // Iterar a través de los Pokémon y obtener el último número de la URL
            pokemonGroupByRegion.pokemonList = pokemonsGeneration.pokemon_species.map { pokemon ->
                val numeroPokemon = getPokemonIdForUrl(pokemon.url)
                PokemonWithId(numeroPokemon,pokemon.name)
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

    suspend fun getEvolutionChainForPokemon(pokemonUrl: String):List<PokemonWithId> = withContext(Dispatchers.IO){
        val call = pokemonClient.getEvolutionChain(pokemonUrl)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val evolutionChain = response.body()
                val evolutionNames = mutableListOf<PokemonWithId>()

                if (evolutionChain != null) {
                    val pokemonId = getPokemonIdForUrl(evolutionChain.chain.species.url)
                    evolutionNames.add(PokemonWithId(pokemonId, evolutionChain.chain.species.name))

                    if (evolutionChain.chain.evolves_to.isNotEmpty()) {
                        val name = evolutionChain.chain.evolves_to[0].species.name
                        val pokemonId = getPokemonIdForUrl(evolutionChain.chain.evolves_to[0].species.url)
                        evolutionNames.add(PokemonWithId(pokemonId, name))

                        if (evolutionChain.chain.evolves_to[0].evolves_to.isNotEmpty()) {
                            val name = evolutionChain.chain.evolves_to[0].evolves_to[0].species.name
                            val pokemonId = getPokemonIdForUrl(evolutionChain.chain.evolves_to[0].evolves_to[0].species.url)
                            evolutionNames.add(PokemonWithId(pokemonId, name))
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

}