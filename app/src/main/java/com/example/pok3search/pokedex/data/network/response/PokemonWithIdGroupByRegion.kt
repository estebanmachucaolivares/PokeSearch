package com.example.pok3search.pokedex.data.network.response

data class PokemonWithIdGroupByRegion(var region: String="", var pokemonList:List<PokemonWithId> = listOf())
