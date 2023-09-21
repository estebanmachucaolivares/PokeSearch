package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.network.PokemonRepository
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import javax.inject.Inject

class GetPokemonDescriptionUseCase @Inject constructor(private val repository: PokemonRepository){
    suspend fun invoke(pokemonId:Int):PokemonDescription{
        return repository.getPokemonDescription(pokemonId)
    }
}