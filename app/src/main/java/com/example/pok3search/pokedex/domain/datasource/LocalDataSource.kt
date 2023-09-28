package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.data.database.entities.PokemonEntity
import com.example.pok3search.pokedex.data.database.entities.RegionEntity
import com.example.pok3search.pokedex.data.database.entities.RegionWithPokemon
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertPokemon(): Boolean
    fun getAllPokemon(): List<PokemonEntity>
    fun getAllRegions(): List<RegionEntity>
    suspend fun insertRegion(region: RegionEntity)
    fun getRegionsWithPokemon(): Flow<List<RegionWithPokemon>>
}