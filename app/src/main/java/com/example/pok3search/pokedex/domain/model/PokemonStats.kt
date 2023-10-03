package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.PokemonStatsEntity
import com.example.pok3search.pokedex.data.network.response.StatsResponse

data class PokemonStats(
    val id: Long = 0,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val speed: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0
)

fun PokemonStatsEntity.toDomain() =
    PokemonStats(id, hp, attack, defense, speed, specialAttack, specialDefense)

