package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.PokemonAbilityEntity
import com.example.pok3search.pokedex.data.network.response.PokemonAbilityResultResponse

data class PokemonAbility(val pokemonId: Int ,val name:String, val description:String)

fun PokemonAbilityResultResponse.toDomain() = PokemonAbility(pokemonId,name,description)

fun PokemonAbilityEntity.toDomain() = PokemonAbility(pokemonId.toInt(),name,description)
