package com.example.pok3search.pokedex.ui.listpokemons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetAllPokemonUseCase
import com.example.pok3search.pokedex.domain.GetPokemonWithRegionUsecase
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokemonViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val getPokemonWithRegionUsecase: GetPokemonWithRegionUsecase
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonGroupByRegion>>()
    val pokemonList:LiveData<List<PokemonGroupByRegion>> = _pokemonList

    fun getAllPokemons(){
        viewModelScope.launch {
            _pokemonList.postValue(getPokemonWithRegionUsecase.invoke())
        }
    }
}