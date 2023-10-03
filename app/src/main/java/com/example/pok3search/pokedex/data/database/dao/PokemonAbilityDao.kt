package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.entities.PokemonAbilityEntity

@Dao
interface PokemonAbilityDao {

    @Query("SELECT * FROM pokemon_ability WHERE pokemonId = :pokemonId")
    suspend fun getPokemonAbility(pokemonId: Int): List<PokemonAbilityEntity>

    @Insert
    suspend fun insertPokemonAbility(pokemonAbility: PokemonAbilityEntity): Long

}