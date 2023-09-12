package com.example.pok3search.pokedex.ui.listpokemons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val getAllPokemonUseCase:GetAllPokemonUseCase) : ViewModel() {

    fun getAllPokemons(){
        viewModelScope.launch {
           val list = getAllPokemonUseCase.invoke()
            Log.d("lista pokemons", "cantidad de pokemnones: ${list.size}")
        }
    }
}