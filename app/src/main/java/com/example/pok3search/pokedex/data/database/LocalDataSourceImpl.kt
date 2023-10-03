package com.example.pok3search.pokedex.data.database

import android.database.sqlite.SQLiteConstraintException
import com.example.pok3search.pokedex.data.database.dao.*
import com.example.pok3search.pokedex.data.database.entities.PokemonEvolutionEntity
import com.example.pok3search.pokedex.data.database.entities.PokemonTypeCrossRef
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
    private val pokemonDescriptionDao: PokemonDescriptionDao,
    private val typeDao: TypeDao,
    private val pokemonEvolutionDao: PokemonEvolutionDao,
    private val pokemonStatsDao: PokemonStatsDao
) : LocalDataSource {
    override suspend fun insertPokemon(pokemon: Pokemon, regionId: Long): Boolean {
        return pokemonDao.insertPokemon(pokemon.toEntity(regionId)) > 0
    }

    override suspend fun getCountOfPokemon(): Int{
        return pokemonDao.getCountOfPokemon()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        return pokemonDao.getAllPokemon().map { it.toDomain() }
    }

    override suspend fun getAllRegions(): List<Region> {
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

    override suspend fun insertType(pokemonType: PokemonTypes): Long {
        return try {
            typeDao.insertType(pokemonType.toEntity())
        } catch (e: SQLiteConstraintException) {
            0
        }
    }

    override suspend fun getType(typeName: String): PokemonTypes? {
        return typeDao.getTypes(typeName)?.toDomain()
    }

    override suspend fun insertPokemonType(pokemonType: PokemonTypes): Boolean {
        return try {
            typeDao.insertPokemonType(
                PokemonTypeCrossRef(
                    pokemonType.pokemonId.toLong(),
                    pokemonType.id
                )
            ) > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun getPokemonType(pokemonId: Int): List<PokemonTypes> {
        return typeDao.getTypesForPokemon(pokemonId.toLong()).map { it.toDomain() }
    }

    override suspend fun insertPokemonEvolution(
        pokemonId: Int,
        evolutionToPokemonId: Int,
        level: Int
    ): Boolean {
        return try {
            pokemonEvolutionDao.insertPokemonEvolution(
                PokemonEvolutionEntity(
                    pokemonId = pokemonId.toLong(),
                    evolutionTo = evolutionToPokemonId.toLong(),
                    level = level
                )
            ) > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun getPokemonEvolution(pokemonId: Int): List<PokemonEvolutionChain> {
        val pokemonEvolutionChain = mutableListOf<PokemonEvolutionChain>()
        val res =  pokemonEvolutionDao.getPokemonEvolution(pokemonId)
        res.forEach{
            val pokemon = pokemonDao.getPokemon(it.evolutionTo.toInt())
           if (pokemon != null){
               pokemonEvolutionChain.add(PokemonEvolutionChain(pokemon.toDomain(),it.level))
           }
        }
        return pokemonEvolutionChain
    }

    override suspend fun insertPokemonStats(pokemonStats: PokemonStats): Boolean {
        return try {
            pokemonStatsDao.insertPokemonStats(
                pokemonStats.toEntity()
            ) > 0
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun getPokemonStats(pokemonId: Int): PokemonStats? {
        return pokemonStatsDao.getPokemonStats(pokemonId)?.toDomain()
    }
}