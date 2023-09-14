package com.example.pok3search.pokedex.data.network

import android.util.Log
import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import com.example.pok3search.pokedex.data.network.response.PokemonWithRegion
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient:PokemonClient)  {

    suspend fun getAllPokemons(): List<PokemonListItem>{
        return pokemonClient.getPokemonList(100).results
    }


    suspend fun getAllPokemonWithRegion(): List<PokemonWithRegion> {
        val pokemonSpeciesResponse = pokemonClient.getAllPokemonSpecies()
        val pokemonList = pokemonSpeciesResponse.results ?: emptyList()
        val pokemonWithRegionList = mutableListOf<PokemonWithRegion>()

        for (pokemon in pokemonList) {
            val pokemonSpecies = pokemonClient.getPokemonSpecies(pokemon.url)
            val generationUrl = pokemonSpecies.generation?.url

            if (!generationUrl.isNullOrBlank()) {
                val generationResponse = pokemonClient.getGenerationByUrl(generationUrl)

                if (generationResponse != null) {
                    pokemonWithRegionList.add(PokemonWithRegion(pokemon.name, generationResponse.main_region.name))
                    Log.d("pokemones",PokemonWithRegion(pokemon.name, generationResponse.main_region.name).toString())
                }
            }
        }

        return pokemonWithRegionList
    }


}