package com.example.pok3search.pokedex.data.network

import android.util.Log
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import com.example.pok3search.pokedex.domain.model.toDomain
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
        val pokemonDescriptions = api.getPokemonDetails(pokemonId)

        val pokemonType = pokemonDescriptions.genera.find { it.language.name == "es" }?.genus ?:""

        val pokemonDescription = pokemonDescriptions.flavor_text_entries.find { it.language.name == "es" }?.flavor_text ?:""

        return PokemonDescription(pokemonType,pokemonDescription)
    }

}