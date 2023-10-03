package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pok3search.pokedex.domain.model.PokemonStats

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

fun PokemonStats.toEntity() =
    PokemonStatsEntity(id, hp, attack, defense, speed, specialAttack, specialDefense)


