package com.example.pok3search.pokedex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pok3search.pokedex.data.database.dao.PokemonDao
import com.example.pok3search.pokedex.data.database.dao.PokemonDescriptionDao
import com.example.pok3search.pokedex.data.database.dao.RegionDao
import com.example.pok3search.pokedex.data.database.dao.TypeDao
import com.example.pok3search.pokedex.data.database.entities.*

@Database(
    entities =
    [PokemonEntity::class,
        RegionEntity::class,
        PokemonDescriptionEntity::class,
        TypeEntity::class,
        PokemonTypeCrossRef::class,
        PokemonEvolutionEntity::class
    ],
    version = 1
)
abstract class PokemonDatabase:RoomDatabase() {
    abstract fun pokemonDao():PokemonDao

    abstract fun regionDao():RegionDao

    abstract fun pokemonDescriptionDao(): PokemonDescriptionDao
    abstract fun typeDao(): TypeDao
}