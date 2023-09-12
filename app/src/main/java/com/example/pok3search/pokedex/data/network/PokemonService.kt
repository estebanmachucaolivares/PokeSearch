package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import javax.inject.Inject

class PokemonService @Inject constructor(private val pokemonClient:PokemonClient)  {

    suspend fun getAllPokemons(): List<PokemonListItem>{
        return pokemonClient.getPokemonList(1281).results
    }
}