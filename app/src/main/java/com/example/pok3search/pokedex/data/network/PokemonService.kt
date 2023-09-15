package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import com.example.pok3search.pokedex.data.network.response.PokemonWithId
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdGroupByRegion
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
                val numeroPokemon = obtenerUltimoNumeroDeUrl(pokemon.url)
                PokemonWithId(numeroPokemon,pokemon.name)
            }.sortedBy{it.id}

            pokemonGroupByRegionList.add(pokemonGroupByRegion)

        }

        return pokemonGroupByRegionList
    }

    fun obtenerUltimoNumeroDeUrl(url: String): Int {
        // Dividir la URL en partes usando "/" como separador
        val partesUrl = url.split("/")

        // El último elemento de la lista será el número que necesitas
        val ultimoElemento = partesUrl[partesUrl.size-2]

        // Intentar convertir el último elemento a un entero y devolverlo
        return try {
            ultimoElemento.toInt()
        } catch (e: NumberFormatException) {
            // Manejo de errores en caso de que el último elemento no sea un número válido
            -1 // Puedes elegir un valor predeterminado o lanzar una excepción aquí
        }
    }

}