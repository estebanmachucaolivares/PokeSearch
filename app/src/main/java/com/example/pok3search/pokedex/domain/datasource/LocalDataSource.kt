package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertPokemon(pokemon: Pokemon, regionId: Long): Boolean
    suspend fun getCountOfPokemon(): Int
    suspend fun getAllPokemon(): List<Pokemon>

    suspend fun getAllRegions(): List<Region>
    suspend fun insertRegion(region: Region):Long
    fun getRegionsWithPokemon(): Flow<List<PokemonGroupByRegion>>

    suspend fun insertPokemonDescription(pokemonDescription: PokemonDescription):Long
    suspend fun getPokemonDescription(pokemonId: Int): PokemonDescription?

    suspend fun insertType(pokemonType: PokemonTypes): Long
    suspend fun getType(typeName: String): PokemonTypes?

    suspend fun insertPokemonType(pokemonType: PokemonTypes): Boolean
    suspend fun getPokemonType(pokemonId: Int): List<PokemonTypes>

    suspend fun insertPokemonEvolution(pokemonId: Int, evolutionToPokemonId: Int, level: Int): Boolean
    suspend fun getPokemonEvolution(pokemonId: Int): List<PokemonEvolutionChain>

    suspend fun insertPokemonStats(pokemonStats: PokemonStats): Boolean
    suspend fun getPokemonStats(pokemonId: Int): PokemonStats?

    suspend fun insertPokemonAbility(pokemonAbility: PokemonAbility): Boolean
    suspend fun getPokemonAbility(pokemonId: Int): List<PokemonAbility>


}