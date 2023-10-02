package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pok3search.pokedex.domain.model.PokemonTypes


@Entity(tableName = "type"
    ,indices = [Index(value = ["typeName"], unique = true)])
data class TypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val typeName: String
)

fun PokemonTypes.toEntity() = TypeEntity(typeName = typeName)