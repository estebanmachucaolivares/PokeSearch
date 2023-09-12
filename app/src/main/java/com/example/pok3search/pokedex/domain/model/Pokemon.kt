package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonListItem

data class Pokemon(val name: String,val url: String)

fun PokemonListItem.toDomain() = Pokemon(name,url)
