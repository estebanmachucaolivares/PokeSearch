package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.toDomain
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api:PokemonService)  {

    suspend fun getAllPokemnons():List<Pokemon>{
        val res = api.getAllPokemons()
        return res.map { it.toDomain() }
    }

    suspend fun getAllPokemonWithRegion():List<Pokemon>{
       return api.getAllPokemonWithRegion().map { it.toDomain() }
    }

}