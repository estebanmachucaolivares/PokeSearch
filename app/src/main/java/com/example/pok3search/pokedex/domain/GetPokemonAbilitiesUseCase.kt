package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.network.PokemonRepository
import com.example.pok3search.pokedex.domain.model.PokemonAbility
import javax.inject.Inject

class GetPokemonAbilitiesUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend fun invoke(pokemonId:Int):List<PokemonAbility>{
        return repository.getPokemonAbilities(pokemonId)
    }
}