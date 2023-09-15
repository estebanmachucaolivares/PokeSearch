package com.example.pok3search.pokedex.data.network.response

data class PokemonGroupByRegion(var region: String="",var pokemonList:List<PokemonListItem> = listOf())
