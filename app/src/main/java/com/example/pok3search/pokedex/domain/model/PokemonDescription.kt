package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.PokemonDescriptionEntity
import com.example.pok3search.pokedex.data.network.response.DescriptionResponse

data class PokemonDescription(val pokemonId: Int, val pokemonType:String, val pokemonDescription: String)

fun DescriptionResponse.toDomain() = PokemonDescription(pokemonId,pokemonType,pokemonDescription)

fun PokemonDescriptionEntity.toDomain() = PokemonDescription(pokemonId, pokemonType, pokemonDescription)