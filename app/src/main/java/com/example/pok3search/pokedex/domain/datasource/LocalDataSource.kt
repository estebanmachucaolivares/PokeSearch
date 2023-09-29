package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import com.example.pok3search.pokedex.domain.model.Region
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertPokemon(pokemon: Pokemon, regionId: Long): Boolean

    suspend fun getCountOfPokemon(): Int
    fun getAllPokemon(): List<Pokemon>
    fun getAllRegions(): List<Region>
    suspend fun insertRegion(region: Region):Long
    fun getRegionsWithPokemon(): Flow<List<PokemonGroupByRegion>>

    suspend fun insertPokemonDescription(pokemonDescription: PokemonDescription):Long
    suspend fun getPokemonDescription(pokemonId: Int): PokemonDescription?

}