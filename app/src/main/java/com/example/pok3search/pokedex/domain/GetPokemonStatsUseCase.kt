package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.PokemonRepository
import com.example.pok3search.pokedex.domain.model.PokemonStats
import javax.inject.Inject

class GetPokemonStatsUseCase @Inject constructor(private val repository: PokemonRepository){
    suspend fun invoke(pokemonId:Int): PokemonStats {
        return repository.getPokemonStats(pokemonId)
    }
}