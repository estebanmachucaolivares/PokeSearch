package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.PokemonEntity
import com.example.pok3search.pokedex.data.network.response.PokemonListItemResponse
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdResponse

data class Pokemon(val id:Int,val name: String)

fun PokemonWithIdResponse.toDomain() = Pokemon(id = id,name = name)

fun PokemonEntity.toDomain() = Pokemon(id = id.toInt(),name = name)