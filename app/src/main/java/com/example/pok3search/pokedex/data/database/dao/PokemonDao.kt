package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonEntity
import com.example.pok3search.pokedex.data.database.entities.PokemonEvolutionEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    suspend fun getPokemon(pokemonId: Int): PokemonEntity?

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getCountOfPokemon(): Int

    @Insert
    suspend fun insertPokemon(pokemon: PokemonEntity): Long

}