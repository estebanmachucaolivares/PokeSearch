package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity

@Entity(tableName = "pokemon_type_crossref", primaryKeys = ["pokemonId", "typeId"])
data class PokemonTypeCrossRef(
    val pokemonId: Long,
    val typeId: Long
)