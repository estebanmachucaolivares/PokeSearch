package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.TypeEntity
import com.example.pok3search.pokedex.data.network.response.PokemonTypeResponse

data class PokemonTypes(val id: Long = 0 ,val pokemonId: Int = 0, val typeName: String = "")

fun PokemonTypeResponse.toDomain() = PokemonTypes(pokemonId = pokemonId, typeName = typeName)

fun TypeEntity.toDomain() = PokemonTypes(id = id, typeName = typeName)


