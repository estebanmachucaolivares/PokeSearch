package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RegionWithPokemon(
    @Embedded val region: RegionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "region_id"
    )
    val pokemonList: List<PokemonEntity>
)