package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonAbilityResultResponse

data class PokemonAbility(val name:String, val description:String)

fun PokemonAbilityResultResponse.toDomain() = PokemonAbility(name,description)
