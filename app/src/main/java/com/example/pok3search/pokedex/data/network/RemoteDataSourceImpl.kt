package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.domain.datasource.RemoteDataSource
import com.example.pok3search.pokedex.domain.model.*
import javax.inject.Inject

class RemoteDataSourceImpl
@Inject constructor(private val api: PokemonService)
    : RemoteDataSource {

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

    override suspend fun getPokemonEvolutionChain(pokemonId: Int): List<PokemonEvolutionChain> {
        val res = api.getEvolutionChainForPokemon(pokemonId)
        return res.map {PokemonEvolutionChain(it.pokemonResponse.toDomain(),it.level) }
    }

    override suspend fun getPokemonStats(pokemonId: Int): PokemonStats {
        val res = api.getPokemonStats(pokemonId)

        val hp = res.stats.find { it.stat.name =="hp" }?.base_stat?.toInt() ?: 0
        val attack = res.stats.find { it.stat.name =="attack" }?.base_stat?.toInt() ?: 0
        val defense = res.stats.find { it.stat.name =="defense" }?.base_stat?.toInt() ?: 0
        val speed = res.stats.find { it.stat.name =="speed" }?.base_stat?.toInt() ?: 0
        val specialAttack = res.stats.find { it.stat.name =="special-attack" }?.base_stat?.toInt() ?: 0
        val specialDefense = res.stats.find{ it.stat.name =="special-defense" }?.base_stat?.toInt() ?: 0

        return PokemonStats(pokemonId.toLong(),hp,attack,defense,speed,specialAttack,specialDefense)
    }

    override suspend fun getPokemonAbilities(pokemonId: Int): List<PokemonAbility> {
        val res = api.getPokemonAbilities(pokemonId)
        return res.map { it.toDomain() }
    }

    override suspend fun getPokemonTypes(pokemonId: Int): List<PokemonTypes> {
        return api.getPokemonTypes(pokemonId).map { it.toDomain() }
    }
}