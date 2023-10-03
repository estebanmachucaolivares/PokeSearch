package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonEvolutionResponse


data class PokemonEvolutionChain(
    val pokemon: Pokemon,
    val level: Int
)

fun PokemonEvolutionResponse.toDomain() = PokemonEvolutionChain(pokemonResponse.toDomain(),level)
