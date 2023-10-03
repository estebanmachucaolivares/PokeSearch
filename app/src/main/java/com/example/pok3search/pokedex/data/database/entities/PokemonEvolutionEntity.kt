package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_evolution",
    indices = [Index(value = ["pokemonId", "evolutionTo", "level"], unique = true)]
)
data class PokemonEvolutionEntity(
    @PrimaryKey( autoGenerate = true)
    val id: Long = 0,
    val pokemonId: Long,
    val evolutionTo: Long,
    val level: Int
)
