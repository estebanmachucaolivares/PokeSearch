package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonStatsEntity

@Dao
interface PokemonStatsDao {

    @Query("SELECT * FROM pokemon_stats WHERE id = :pokemonId")
    suspend fun getPokemonStats(pokemonId: Int): PokemonStatsEntity?

    @Insert
    suspend fun insertPokemonStats(pokemonStats: PokemonStatsEntity): Long
}