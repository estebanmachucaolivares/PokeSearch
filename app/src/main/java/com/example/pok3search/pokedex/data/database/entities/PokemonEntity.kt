package com.example.pok3search.pokedex.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pok3search.pokedex.domain.model.Pokemon

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "region_id")
    val regionId: Long
)

fun Pokemon.toEntity(regionId: Long) = PokemonEntity(id.toLong(),name, regionId)
