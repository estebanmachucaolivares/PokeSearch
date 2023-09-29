package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.PokemonRepository
import javax.inject.Inject

class SavePokemonWithRegionUseCase @Inject constructor(private val repository: PokemonRepository) {
    suspend operator fun invoke(): Boolean = repository.insertAllPokemonWithRegion()
}