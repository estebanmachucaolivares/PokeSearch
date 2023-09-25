package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonTypeResponse

data class PokemonTypes(val pokemonId: Int, val typeName: String)

fun PokemonTypeResponse.toDomain() = PokemonTypes(pokemonId,typeName)
