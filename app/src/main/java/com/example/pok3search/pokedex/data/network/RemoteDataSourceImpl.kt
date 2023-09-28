package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.domain.datasource.RemoteDataSource
import com.example.pok3search.pokedex.domain.model.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: PokemonService) : RemoteDataSource {

    override suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion> {
        return api.getAllPokemonWithRegion().map {
            PokemonGroupByRegion(
                it.region,
                it.pokemonList.map { pokemonList -> pokemonList.toDomain() })
        }
    }

    override suspend fun getPokemonDescription(pokemonId: Int): PokemonDescription {
        return api.getPokemonDetails(pokemonId).toDomain()
    }

    override suspend fun getEvolutionChainForPokemon(pokemonId: Int): List<Pokemon> {
        val res = api.getEvolutionChainForPokemon(pokemonId)
        return res.map { it.toDomain() }
    }

    override suspend fun getPokemonStats(pokemonId: Int): List<PokemonStats> {
        val res = api.getPokemonStats(pokemonId)
        return res.stats.map { it.toDomain(pokemonId) }
    }

    override suspend fun getPokemonAbilities(pokemonId: Int): List<PokemonAbility> {
        val res = api.getPokemonAbilities(pokemonId)
        return res.map { it.toDomain() }
    }

    override suspend fun getPokemonTypes(pokemonId: Int): List<PokemonTypes> {
        return api.getPokemonTypes(pokemonId).map { it.toDomain() }
    }
}