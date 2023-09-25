package com.example.pok3search.pokedex.ui.detailpokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.*
import com.example.pok3search.pokedex.domain.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase,
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase,
    private val getPokemonStatsUseCase: GetPokemonStatsUseCase,
    private val getPokemonAbilitiesUseCase: GetPokemonAbilitiesUseCase,
    private val getPokemonTypeUseCase: GetPokemonTypeUseCase
) : ViewModel() {

    private val _pokemonDescription = MutableLiveData<PokemonDescription>()
    val pokemonDescription: LiveData<PokemonDescription> = _pokemonDescription

    private val _pokemonEvolutionChain = MutableLiveData<List<Pokemon>>()
    val pokemonEvolutionChain : LiveData<List<Pokemon>> = _pokemonEvolutionChain

    private val _pokemonStats = MutableLiveData<List<PokemonStats>>()
    val pokemonStats : LiveData<List<PokemonStats>> = _pokemonStats

    private val _pokemonAbilities = MutableLiveData<List<PokemonAbility>>()
    val pokemonAbilities : LiveData<List<PokemonAbility>> = _pokemonAbilities

    private val _pokemonTypes = MutableLiveData<List<PokemonTypes>>()
    val pokemonTypes : LiveData<List<PokemonTypes>> = _pokemonTypes

    fun getPokemonDescription(pokemonId:Int){
        viewModelScope.launch {
            _pokemonDescription.postValue(getPokemonDescriptionUseCase.invoke(pokemonId))
        }
    }

    fun getPokemonEvolutionChain(pokemonId:Int){
        viewModelScope.launch {
            _pokemonEvolutionChain.postValue(getEvolutionChainUseCase.invoke(pokemonId))
        }
    }

    fun getPokemonStats(pokemonId:Int){
        viewModelScope.launch {
            _pokemonStats.postValue(getPokemonStatsUseCase.invoke(pokemonId))
        }
    }

    fun getPokemonAbility(pokemonId:Int){
        viewModelScope.launch {
            _pokemonAbilities.postValue(getPokemonAbilitiesUseCase.invoke(pokemonId))
        }
    }

    fun getPokemonType(pokemonId:Int){
        viewModelScope.launch {
            _pokemonTypes.postValue(getPokemonTypeUseCase.invoke(pokemonId))
        }
    }

    fun clearPokemonDetail() {
        _pokemonDescription.value = PokemonDescription("","")
        _pokemonEvolutionChain.value = emptyList()
        _pokemonStats.value = emptyList()
        _pokemonAbilities.value = emptyList()
        _pokemonTypes.value = emptyList()
    }

}