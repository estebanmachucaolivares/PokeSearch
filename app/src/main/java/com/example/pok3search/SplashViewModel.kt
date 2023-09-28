package com.example.pok3search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.SavePokemonWithRegionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor( private val savePokemonWithRegionUseCase: SavePokemonWithRegionUseCase): ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            mutableStateFlow.value = false
        }
    }
}