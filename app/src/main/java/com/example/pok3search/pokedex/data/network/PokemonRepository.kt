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
        val pokemonDescriptions = api.getPokemonDetails(pokemonId)

        val pokemonType = pokemonDescriptions.genera.find { it.language.name == "es" }?.genus ?:
        pokemonDescriptions.genera.find { it.language.name == "en" }?.genus ?: "Sin Información"

        val pokemonDescription = pokemonDescriptions.flavor_text_entries.find { it.language.name == "es" }?.flavor_text ?:
        pokemonDescriptions.flavor_text_entries.find { it.language.name == "en" }?.flavor_text ?:"Sin Información"

        return PokemonDescription(pokemonType,pokemonDescription,pokemonDescriptions.evolution_chain.url)
    }


    suspend fun getEvolutionChainForPokemon(pokemonUrl: String):List<Pokemon>{
        val res = api.getEvolutionChainForPokemon(pokemonUrl)
        return res.map { it.toDomain() }
    }

    suspend fun getPokemonStats(pokemonId: Int):List<PokemonStats>{
        val res = api.getPokemonStats(pokemonId)
        return res.stats.map { it.toDomain() }
    }

}