package com.example.pok3search.pokedex.ui.detailpokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetEvolutionChainUseCase
import com.example.pok3search.pokedex.domain.GetPokemonDescriptionUseCase
import com.example.pok3search.pokedex.domain.GetPokemonStatsUseCase
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import com.example.pok3search.pokedex.domain.model.PokemonStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase,
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase,
    private val getPokemonStatsUseCase: GetPokemonStatsUseCase
) : ViewModel() {

    private val _pokemonDescription = MutableLiveData<PokemonDescription>()
    val pokemonDescription: LiveData<PokemonDescription> = _pokemonDescription

    private val _pokemonEvolutionChain = MutableLiveData<List<Pokemon>>()
    val pokemonEvolutionChain : LiveData<List<Pokemon>> = _pokemonEvolutionChain

    private val _pokemonStats = MutableLiveData<List<PokemonStats>>()
    val pokemonStats : LiveData<List<PokemonStats>> = _pokemonStats

    fun getPokemonDescription(pokemonId:Int){
        viewModelScope.launch {
            _pokemonDescription.postValue(getPokemonDescriptionUseCase.invoke(pokemonId))
        }
    }

    fun getPokemonEvolutionChain(pokemonEvolutionUrl:String){
        viewModelScope.launch {
            _pokemonEvolutionChain.postValue(getEvolutionChainUseCase.invoke(pokemonEvolutionUrl))
        }
    }

    fun getPokemonStats(pokemonId:Int){
        viewModelScope.launch {
            val pokemonStats = getPokemonStatsUseCase.invoke(pokemonId)
            Log.d("pokemon stats", pokemonStats.toString())
            _pokemonStats.postValue(pokemonStats)
        }
    }

}