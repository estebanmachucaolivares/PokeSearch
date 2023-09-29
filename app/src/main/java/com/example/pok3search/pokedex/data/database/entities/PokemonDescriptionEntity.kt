package com.example.pok3search.pokedex.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import com.example.pok3search.pokedex.domain.model.PokemonDescription

@Entity(tableName = "description"
    ,indices = [Index(value = ["pokemonType", "pokemonDescription"], unique = true)])
data class PokemonDescriptionEntity(
    @PrimaryKey( autoGenerate = true)
    val id: Long = 0,
    val pokemonType:String,
    val pokemonDescription: String,
    @ColumnInfo(name = "pokemon_id")
    val pokemonId: Int
)

fun PokemonDescription.toEntity() = PokemonDescriptionEntity(pokemonType = pokemonType, pokemonDescription = pokemonDescription, pokemonId = pokemonId)