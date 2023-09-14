package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.network.PokemonRepository
import com.example.pok3search.pokedex.domain.model.Pokemon
import javax.inject.Inject

class GetPokemonWithRegionUsecase @Inject constructor(private val repository: PokemonRepository) {

    suspend fun invoke():List<Pokemon>{
        return repository.getAllPokemonWithRegion()
    }

}
