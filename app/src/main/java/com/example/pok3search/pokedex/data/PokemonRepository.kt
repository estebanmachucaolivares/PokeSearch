package com.example.pok3search.pokedex.data

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

    suspend fun getPokemonDescription(pokemonId:Int): PokemonDescription? {
        val pokemonDescription = localDataSource.getPokemonDescription(pokemonId)
        return if(pokemonDescription != null){
            pokemonDescription
        }else{
            val pokemonDescriptionRemote = remoteDataSource.getPokemonDescription(pokemonId)
            pokemonDescriptionRemote?.let {
                localDataSource.insertPokemonDescription(it)
            }
            pokemonDescriptionRemote
        }
    }

    suspend fun getEvolutionChainForPokemon(pokemonId: Int): List<PokemonEvolutionChain> {
        val pokemonEvolutionChain = localDataSource.getPokemonEvolutionChain(pokemonId)

        return pokemonEvolutionChain.ifEmpty {
            val pokemonEvolutionChainApi = remoteDataSource.getPokemonEvolutionChain(pokemonId)

            if(pokemonEvolutionChainApi.size > 1){
                pokemonEvolutionChainApi.forEach {
                    localDataSource.insertPokemonEvolution(pokemonId, it.pokemon.id, it.level)
                }

                pokemonEvolutionChainApi
            }else{
                emptyList()
            }
        }
    }

    suspend fun getPokemonStats(pokemonId: Int): PokemonStats? {
        val pokemonStats = localDataSource.getPokemonStats(pokemonId)

        return if (pokemonStats != null) {
            pokemonStats
        } else {
            val pokemonStatsApi = remoteDataSource.getPokemonStats(pokemonId)
            pokemonStatsApi?.let {
                localDataSource.insertPokemonStats(it)
            }
            pokemonStatsApi
        }
    }

    suspend fun getPokemonAbilities(pokemonId: Int): List<PokemonAbility> {

        val pokemonAbility = localDataSource.getPokemonAbilities(pokemonId)

        return pokemonAbility.ifEmpty {

            val pokemonAbilityApi = remoteDataSource.getPokemonAbilities(pokemonId)

            pokemonAbilityApi.forEach {
                localDataSource.insertPokemonAbility(it)
            }
            return pokemonAbilityApi
        }
    }

    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>{
        val pokemonType =  localDataSource.getPokemonTypes(pokemonId)

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