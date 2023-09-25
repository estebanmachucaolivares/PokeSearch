package com.example.pok3search.pokedex.data.network.response


data class PokemonEvolutionsResponse(val evolution_chain:PokemonEvolutionUrlResponse)
data class EvolutionChain(
    val chain: Chain
)

data class Chain(
    val evolves_to: List<Evolution>,
    val species: Species
)

data class Evolution(
    val evolves_to: List<Evolution>,
    val species: Species
)

data class Species(
    val name: String,
    val url: String
)