package com.example.pok3search.pokedex.domain.model

import com.example.pok3search.pokedex.data.database.entities.RegionEntity

data class Region(val id: Long, val name: String)

fun RegionEntity.toDomain() = Region(id,name)
