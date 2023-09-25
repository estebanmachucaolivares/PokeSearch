package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.DescriptionResponse

data class PokemonDescription(val pokemonType:String, val pokemonDescription: String, val pokemonEvolutionUrl:String)

fun DescriptionResponse.toDomain() = PokemonDescription(pokemonType,pokemonDescription,pokemonEvolutionUrl)