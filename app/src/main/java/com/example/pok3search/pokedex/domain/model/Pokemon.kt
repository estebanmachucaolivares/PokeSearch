package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import com.example.pok3search.pokedex.data.network.response.PokemonWithRegion

data class Pokemon(val name: String,val url: String = "", val region: String = "")

fun PokemonListItem.toDomain() = Pokemon(name= name, url = url)

fun PokemonWithRegion.toDomain() = Pokemon(name = name,region = region)
