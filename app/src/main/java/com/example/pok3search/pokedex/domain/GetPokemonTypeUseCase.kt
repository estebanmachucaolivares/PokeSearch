package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.network.PokemonRepository
import com.example.pok3search.pokedex.domain.model.PokemonTypes
import javax.inject.Inject

class GetPokemonTypeUseCase @Inject constructor(private val repository: PokemonRepository) {

    suspend fun invoke(pokemonId:Int):List<PokemonTypes>{
        return repository.getPokemonTypes(pokemonId)
    }
}