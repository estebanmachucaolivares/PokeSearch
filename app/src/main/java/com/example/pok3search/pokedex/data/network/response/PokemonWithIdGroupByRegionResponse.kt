package com.example.pok3search.pokedex.data.network.response

data class PokemonWithIdGroupByRegionResponse(var region: String="", var pokemonList:List<PokemonWithIdResponse> = listOf())
