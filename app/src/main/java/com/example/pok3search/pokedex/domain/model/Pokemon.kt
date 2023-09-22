package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.PokemonListItemResponse
import com.example.pok3search.pokedex.data.network.response.PokemonWithIdResponse

data class Pokemon(val id:Int=0,val name: String,val url: String = "", val region: String = "")

fun PokemonListItemResponse.toDomain() = Pokemon(name= name, url = url)
fun PokemonWithIdResponse.toDomain() = Pokemon(id = id,name = name)