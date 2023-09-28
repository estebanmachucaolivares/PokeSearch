package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region")
data class RegionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)