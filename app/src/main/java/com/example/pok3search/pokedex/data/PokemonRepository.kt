package com.example.pok3search.pokedex.data

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

    suspend fun getEvolutionChainForPokemon(pokemonId:Int):List<Pokemon>{
        return remoteDataSource.getEvolutionChainForPokemon(pokemonId)
    }

    suspend fun getPokemonStats(pokemonId: Int):List<PokemonStats>{
        return remoteDataSource.getPokemonStats(pokemonId)
    }

    suspend fun getPokemonAbilities(pokemonId: Int):List<PokemonAbility>{
        return remoteDataSource.getPokemonAbilities(pokemonId)
    }

    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>{
        return remoteDataSource.getPokemonTypes(pokemonId)
    }

}