package com.example.pok3search.pokedex.data.network.response

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonListItem>
)