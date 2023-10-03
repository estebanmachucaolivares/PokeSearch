package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.PokemonRepository
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonEvolutionChain
import javax.inject.Inject

class GetEvolutionChainUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend fun invoke(pokemonId:Int): List<PokemonEvolutionChain>{
        return repository.getEvolutionChainForPokemon(pokemonId)
    }
}