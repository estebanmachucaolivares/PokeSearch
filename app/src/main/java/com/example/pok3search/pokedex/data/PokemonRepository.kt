package com.example.pok3search.pokedex.data

import android.util.Log
import com.example.pok3search.pokedex.data.database.entities.toEntity
import com.example.pok3search.pokedex.domain.model.*
import javax.inject.Inject
import com.example.pok3search.pokedex.domain.datasource.LocalDataSource
import com.example.pok3search.pokedex.domain.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow


class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    val pokemonWithRegion: Flow<List<PokemonGroupByRegion>> = localDataSource.getRegionsWithPokemon()

    suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion> {
        return remoteDataSource.getAllPokemonWithRegion()
    }


    suspend fun insertAllPokemonWithRegion(): Boolean {
        return try {
            if(localDataSource.getCountOfPokemon() == 0){
                val pokemonWithRegion = remoteDataSource.getAllPokemonWithRegion()

                pokemonWithRegion.forEach { region ->
                    val regionId = localDataSource.insertRegion(Region(name = region.region))
                    region.pokemonList.forEach { pokemon ->
                        localDataSource.insertPokemon(pokemon = Pokemon(pokemon.id,pokemon.name), regionId = regionId)
                    }
                }
            }

            true // Operación exitosa

        } catch (e: Exception) {
            false // Ocurrió un error durante la operación
        }
    }

    suspend fun getPokemonDescription(pokemonId:Int): PokemonDescription{
        val pokemonDescription = localDataSource.getPokemonDescription(pokemonId)
        return if(pokemonDescription != null){
            pokemonDescription
        }else{
            val pokemonDescriptionRemote = remoteDataSource.getPokemonDescription(pokemonId)
            localDataSource.insertPokemonDescription(pokemonDescriptionRemote)
            pokemonDescriptionRemote
        }
    }

    suspend fun getEvolutionChainForPokemon(pokemonId: Int): List<PokemonEvolutionChain> {
        val pokemonEvolutionChain = localDataSource.getPokemonEvolution(pokemonId)
        Log.d("evolucion", "pokemonEvolutionChain $pokemonEvolutionChain")
        return pokemonEvolutionChain.ifEmpty {

            Log.d("evolucion", "ifEmpty")
            val pokemonEvolutionChainApi = remoteDataSource.getEvolutionChainForPokemon(pokemonId)

            Log.d("evolucion", "ifEmpty $pokemonEvolutionChainApi")
            pokemonEvolutionChainApi.forEach {
                Log.d("evolucion", "forEach ${it.pokemon}")

                val insert = localDataSource.insertPokemonEvolution(pokemonId, it.pokemon.id, it.level)
                Log.d("evolucion", "insert $insert")
            }

            pokemonEvolutionChainApi
        }
    }

    suspend fun getPokemonStats(pokemonId: Int): PokemonStats {
        val pokemonStats = localDataSource.getPokemonStats(pokemonId)

        return if (pokemonStats != null) {
            pokemonStats
        } else {
            val pokemonStatsApi = remoteDataSource.getPokemonStats(pokemonId)
            localDataSource.insertPokemonStats(pokemonStatsApi)
            pokemonStatsApi
        }
    }

    suspend fun getPokemonAbilities(pokemonId: Int):List<PokemonAbility>{
        return remoteDataSource.getPokemonAbilities(pokemonId)
    }

    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>{
        val pokemonType =  localDataSource.getPokemonType(pokemonId)

        return pokemonType.ifEmpty {
            val pokemonTypeForApi = remoteDataSource.getPokemonTypes(pokemonId)

            pokemonTypeForApi.forEach {
                val type = localDataSource.getType(it.typeName)
                if (type != null) {
                    localDataSource.insertPokemonType(
                        PokemonTypes(
                            id = type.id,
                            pokemonId = pokemonId
                        )
                    )
                } else {
                    val typeId = localDataSource.insertType(it)
                    if (typeId > 0) {
                        localDataSource.insertPokemonType(
                            PokemonTypes(
                                id = typeId,
                                pokemonId = pokemonId
                            )
                        )
                    }
                }
            }

            pokemonTypeForApi
        }
    }

}