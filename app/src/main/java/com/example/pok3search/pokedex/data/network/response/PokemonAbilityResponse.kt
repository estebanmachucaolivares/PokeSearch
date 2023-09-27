package com.example.pok3search.pokedex.data.network.response


data class PokemonAbilityUrlResponse(val abilities: List<Ability>)

data class Ability(val ability:AbilityDetail)

data class AbilityDetail(val name:String,val url:String)

data class PokemonAbilityResponse(val flavor_text_entries:List<AbilityTextEntries> ,val names:List<AbilityName>)

data class AbilityTextEntries(val flavor_text:String, val language: PokemonLanguage)

data class AbilityName(val language:PokemonLanguage,val name:String)

data class PokemonAbilityResultResponse(val pokemonId: Int, val name:String = "",val description:String = "")