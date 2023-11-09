package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocalDataSource: BaseDataSource {
    suspend fun insertPokemon(pokemon: Pokemon, regionId: Long): Boolean
    suspend fun getCountOfPokemon(): Int
    suspend fun getAllPokemon(): List<Pokemon>

    suspend fun getAllRegions(): List<Region>
    suspend fun insertRegion(region: Region):Long
    fun getRegionsWithPokemon(): Flow<List<PokemonGroupByRegion>>

    suspend fun insertPokemonDescription(pokemonDescription: PokemonDescription):Long

    suspend fun insertType(pokemonType: PokemonTypes): Long
    suspend fun getType(typeName: String): PokemonTypes?

    suspend fun insertPokemonType(pokemonType: PokemonTypes): Boolean

    suspend fun insertPokemonEvolution(pokemonId: Int, evolutionToPokemonId: Int, level: Int): Boolean

    suspend fun insertPokemonStats(pokemonStats: PokemonStats): Boolean

    suspend fun insertPokemonAbility(pokemonAbility: PokemonAbility): Boolean


}