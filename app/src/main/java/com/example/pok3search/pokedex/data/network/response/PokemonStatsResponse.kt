package com.example.pok3search.pokedex.data.network.response

data class PokemonStatsResponse(val stats: List<StatsResponse>)

data class StatsResponse(val base_stat:String,val stat:StatResponse)

data class StatResponse(val name:String)




