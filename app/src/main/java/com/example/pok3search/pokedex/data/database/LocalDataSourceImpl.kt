package com.example.pok3search.pokedex.data.database

import android.database.sqlite.SQLiteConstraintException
import com.example.pok3search.pokedex.data.database.dao.PokemonDao
import com.example.pok3search.pokedex.data.database.dao.PokemonDescriptionDao
import com.example.pok3search.pokedex.data.database.dao.RegionDao
import com.example.pok3search.pokedex.data.database.entities.RegionWithPokemon
import com.example.pok3search.pokedex.data.database.entities.toEntity
import com.example.pok3search.pokedex.domain.datasource.LocalDataSource
import com.example.pok3search.pokedex.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val regionDao: RegionDao,
    private val pokemonDao: PokemonDao,
    private val pokemonDescriptionDao: PokemonDescriptionDao
) : LocalDataSource {
    override suspend fun insertPokemon(pokemon: Pokemon, regionId: Long): Boolean {
        return pokemonDao.insertPokemon(pokemon.toEntity(regionId)) > 0
    }

    override suspend fun getCountOfPokemon(): Int{
        return pokemonDao.getCountOfPokemon()
    }

    override fun getAllPokemon(): List<Pokemon> {
        return pokemonDao.getAllPokemon().map { it.toDomain() }
    }

    override fun getAllRegions(): List<Region> {
        return regionDao.getAllRegions().map { it.toDomain() }
    }

    override suspend fun insertRegion(region: Region):Long{
        return regionDao.insertRegion(region.toEntity())
    }

    override fun getRegionsWithPokemon(): Flow<List<PokemonGroupByRegion>> {
        return regionDao.getRegionsWithPokemon().map {
            it.map { regionWithPokemon ->
                PokemonGroupByRegion(
                    regionWithPokemon.region.name,
                    regionWithPokemon.pokemonList.map { pokemonList -> pokemonList.toDomain() })
            }
        }
    }

    override suspend fun insertPokemonDescription(pokemonDescription: PokemonDescription): Long {
        return try {
            pokemonDescriptionDao.insertPokemonDescription(pokemonDescription.toEntity())
        } catch (e: SQLiteConstraintException) {
            0
        }
    }

    override suspend fun getPokemonDescription(pokemonId: Int): PokemonDescription? {
        return pokemonDescriptionDao.getPokemonDescription(pokemonId)?.toDomain()
    }
}