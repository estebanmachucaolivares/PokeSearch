package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.*

interface RemoteDataSource {
    suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion>
    suspend fun getPokemonDescription(pokemonId:Int): PokemonDescription
    suspend fun getEvolutionChainForPokemon(pokemonId:Int):List<Pokemon>
    suspend fun getPokemonStats(pokemonId: Int):List<PokemonStats>
    suspend fun getPokemonAbilities(pokemonId: Int):List<PokemonAbility>
    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>
}