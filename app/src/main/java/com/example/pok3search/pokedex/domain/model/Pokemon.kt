package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonListItem
import com.example.pok3search.pokedex.data.network.response.PokemonWithId

data class Pokemon(val id:Int=0,val name: String,val url: String = "", val region: String = "")

fun PokemonListItem.toDomain() = Pokemon(name= name, url = url)
fun PokemonWithId.toDomain() = Pokemon(id = id,name = name)