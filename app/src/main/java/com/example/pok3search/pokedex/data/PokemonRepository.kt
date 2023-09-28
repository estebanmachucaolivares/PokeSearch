package com.example.pok3search.pokedex.data

import com.example.pok3search.pokedex.data.database.dao.PokemonDao
import com.example.pok3search.pokedex.data.database.dao.RegionDao
import com.example.pok3search.pokedex.data.database.entities.PokemonEntity
import com.example.pok3search.pokedex.data.database.entities.RegionEntity
import com.example.pok3search.pokedex.data.network.PokemonService
import com.example.pok3search.pokedex.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.example.pok3search.pokedex.domain.Result
import kotlinx.coroutines.flow.map

class PokemonRepository @Inject constructor(
    private val api: PokemonService,
    private val regionDao: RegionDao,
    private val pokemonDao: PokemonDao
) {

    /*val pokemonWithRegion: Flow<Result<List<PokemonGroupByRegion>>> =
        regionDao.getRegionsWithPokemon().map {
            it.map { regionWithPokemon ->
                PokemonGroupByRegion(
                    regionWithPokemon.region.name,
                    regionWithPokemon.pokemonList.map { pokemonEntity ->
                        pokemonEntity.toDomain()
                    }
                )
            }
        }*/

    suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion> {
        return api.getAllPokemonWithRegion().map {
            PokemonGroupByRegion(
                it.region,
                it.pokemonList.map { pokemonList -> pokemonList.toDomain() })
        }
    }


    /*suspend fun insertAllPokemonWithRegion(): Boolean {
        return try {
            val pokemonWithRegion = api.getAllPokemonWithRegion()

            pokemonWithRegion.forEach { region ->
                val regionId = regionDao.insertRegion(RegionEntity(name = region.region))
                region.pokemonList.forEach { pokemon ->
                    pokemonDao.insertPokemon(PokemonEntity(name = pokemon.name, regionId = regionId))
                }
            }

            true // Operación exitosa
        } catch (e: Exception) {
            false // Ocurrió un error durante la operación
        }
    }*/

    suspend fun getPokemonDescription(pokemonId:Int): PokemonDescription{
        return api.getPokemonDetails(pokemonId).toDomain()
    }

    suspend fun getEvolutionChainForPokemon(pokemonId:Int):List<Pokemon>{
        val res = api.getEvolutionChainForPokemon(pokemonId)
        return res.map { it.toDomain() }
    }

    suspend fun getPokemonStats(pokemonId: Int):List<PokemonStats>{
        val res = api.getPokemonStats(pokemonId)
        return res.stats.map { it.toDomain(pokemonId) }
    }

    suspend fun getPokemonAbilities(pokemonId: Int):List<PokemonAbility>{
        val res = api.getPokemonAbilities(pokemonId)
        return res.map { it.toDomain() }
    }

    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>{
        return api.getPokemonTypes(pokemonId).map { it.toDomain() }
    }

}