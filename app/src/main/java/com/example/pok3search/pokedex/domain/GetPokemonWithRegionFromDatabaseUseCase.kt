package com.example.pok3search.pokedex.domain

import com.example.pok3search.pokedex.data.PokemonRepository
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonWithRegionFromDatabaseUseCase @Inject constructor(private val repository: PokemonRepository) {
    operator fun invoke(): Flow<List<PokemonGroupByRegion>> = repository.pokemonWithRegion
}