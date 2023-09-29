package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonDescriptionEntity

@Dao
interface PokemonDescriptionDao {

    @Query("SELECT * FROM description WHERE pokemon_id = :pokemonId")
    suspend fun getPokemonDescription(pokemonId: Int): PokemonDescriptionEntity?

    @Insert
    suspend fun insertPokemonDescription(pokemonDescription: PokemonDescriptionEntity): Long
}