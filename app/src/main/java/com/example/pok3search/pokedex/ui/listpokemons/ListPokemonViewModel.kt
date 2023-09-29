package com.example.pok3search.pokedex.ui.listpokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetPokemonWithRegionFromDatabaseUseCase
import com.example.pok3search.pokedex.domain.SavePokemonWithRegionUseCase
import com.example.pok3search.pokedex.ui.PokemonWithRegionUiState
import com.example.pok3search.pokedex.ui.PokemonWithRegionUiState.Success
import com.example.pok3search.pokedex.ui.PokemonWithRegionUiState.Error
import com.example.pok3search.pokedex.ui.PokemonWithRegionUiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val getPokemonWithRegionFromDatabaseUseCase: GetPokemonWithRegionFromDatabaseUseCase,
    private val savePokemonWithRegionUseCase: SavePokemonWithRegionUseCase
) : ViewModel() {

    val pokemonWithRegionUiState: StateFlow<PokemonWithRegionUiState> =
        getPokemonWithRegionFromDatabaseUseCase()
            .map (::Success)
            .catch { Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),Loading)

    fun getAllPokemon(){
        viewModelScope.launch {
            savePokemonWithRegionUseCase()
        }
    }
}