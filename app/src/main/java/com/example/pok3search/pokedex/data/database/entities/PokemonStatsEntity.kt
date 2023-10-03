package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_stats")
data class PokemonStatsEntity(
    @PrimaryKey
    val id: Long,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    val specialAttack: Int,
    val specialDefense: Int,
)


