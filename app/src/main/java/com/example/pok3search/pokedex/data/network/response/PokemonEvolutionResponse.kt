package com.example.pok3search.pokedex.data.network.response

data class PokemonEvolutionResponse(
    val pokemonResponse: PokemonWithIdResponse,
    val level: Int
)