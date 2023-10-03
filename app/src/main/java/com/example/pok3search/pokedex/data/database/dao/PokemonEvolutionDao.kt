package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonEvolutionEntity

@Dao
interface PokemonEvolutionDao {

    @Query("SELECT * FROM pokemon_evolution WHERE pokemonId = :pokemonId")
    suspend fun getPokemonEvolution(pokemonId: Int): List<PokemonEvolutionEntity>

    @Insert
    suspend fun insertPokemonEvolution(pokemonEvolution: PokemonEvolutionEntity): Long
}