package com.example.pok3search.pokedex.data.network.response

data class PokemonTypeResponse(val pokemonId: Int, val typeName: String)

data class PokemonTypeUrlResponse(val types:List<TypeItems>)

data class TypeItems(val type:TypeDetail)

data class TypeDetail(val url:String)

data class PokemonTypesName(val names:List<PokemonTypeItems>)

data class PokemonTypeItems(val language: PokemonLanguage, val name:String)