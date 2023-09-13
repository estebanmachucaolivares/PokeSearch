package com.example.pok3search.pokedex.ui.listpokemons

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetAllPokemonUseCase
import com.example.pok3search.pokedex.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(private val getAllPokemonUseCase:GetAllPokemonUseCase) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()

    val pokemonList:LiveData<List<Pokemon>> = _pokemonList

    fun getAllPokemons(){
        viewModelScope.launch {
           val list = getAllPokemonUseCase.invoke()
            _pokemonList.postValue(list)
            Log.d("lista pokemons", "cantidad de pokemnones: ${list.size}")
        }
    }
}