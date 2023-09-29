package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getCountOfPokemon(): Int

    @Insert
    suspend fun insertPokemon(pokemon: PokemonEntity): Long

}