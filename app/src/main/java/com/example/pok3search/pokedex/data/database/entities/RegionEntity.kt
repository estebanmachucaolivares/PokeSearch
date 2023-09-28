package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pok3search.pokedex.domain.model.Region

@Entity(tableName = "region")
data class RegionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)


fun Region.toEntity() = RegionEntity(name =name)