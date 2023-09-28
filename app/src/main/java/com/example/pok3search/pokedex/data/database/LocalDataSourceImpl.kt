package com.example.pok3search.pokedex.data.database

import com.example.pok3search.pokedex.data.database.entities.PokemonEntity
import com.example.pok3search.pokedex.data.database.entities.RegionEntity
import com.example.pok3search.pokedex.data.database.entities.RegionWithPokemon
import com.example.pok3search.pokedex.domain.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(): LocalDataSource {
    override suspend fun insertPokemon(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getAllPokemon(): List<PokemonEntity> {
        TODO("Not yet implemented")
    }

    override fun getAllRegions(): List<RegionEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertRegion(region: RegionEntity) {
        TODO("Not yet implemented")
    }

    override fun getRegionsWithPokemon(): Flow<List<RegionWithPokemon>> {
        TODO("Not yet implemented")
    }
}