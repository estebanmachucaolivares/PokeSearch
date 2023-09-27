package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.domain.model.*
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api:PokemonService)  {

    suspend fun getAllPokemnons():List<Pokemon>{
        val res = api.getAllPokemons()
        return res.map { it.toDomain() }
    }

    suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion> {
        return api.getAllPokemonWithRegion().map {
            PokemonGroupByRegion(
                it.region,
                it.pokemonList.map { pokemonList -> pokemonList.toDomain() })
        }
    }

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