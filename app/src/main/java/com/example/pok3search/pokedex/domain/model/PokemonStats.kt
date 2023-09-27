package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.StatsResponse

data class PokemonStats(val pokemonId: Int,val name:String, val baseStat:Int)

fun StatsResponse.toDomain(pokemonId:Int) = PokemonStats(pokemonId,name = stat.name, baseStat = base_stat.toInt())
