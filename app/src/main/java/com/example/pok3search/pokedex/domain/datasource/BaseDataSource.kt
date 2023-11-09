package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.*

interface BaseDataSource {
    suspend fun getPokemonDescription(pokemonId: Int): PokemonDescription?

    suspend fun getPokemonStats(pokemonId: Int): PokemonStats?

    suspend fun getPokemonAbilities(pokemonId: Int):List<PokemonAbility>

    suspend fun getPokemonTypes(pokemonId: Int):List<PokemonTypes>

    suspend fun getPokemonEvolutionChain(pokemonId:Int):List<PokemonEvolutionChain>
}