package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.network.response.StatsResponse

data class PokemonStats(val name:String, val baseStat:Int)

fun StatsResponse.toDomain() = PokemonStats(name = stat.name, baseStat = base_stat.toInt())
