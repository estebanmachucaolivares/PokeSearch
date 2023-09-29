package com.example.pok3search.pokedex.ui

import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion

sealed interface PokemonWithRegionUiState{
    object Loading:PokemonWithRegionUiState
    data class Error(val throwable : Throwable):PokemonWithRegionUiState
    data class Success(val pokemonWithRegion: List<PokemonGroupByRegion>):PokemonWithRegionUiState
}