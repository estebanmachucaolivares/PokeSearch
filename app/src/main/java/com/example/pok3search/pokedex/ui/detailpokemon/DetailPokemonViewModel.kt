package com.example.pok3search.pokedex.ui.detailpokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetEvolutionChainUseCase
import com.example.pok3search.pokedex.domain.GetPokemonDescriptionUseCase
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase,
    private val getEvolutionChainUseCase: GetEvolutionChainUseCase
) : ViewModel() {

    private val _pokemonDescription = MutableLiveData<PokemonDescription>()
    val pokemonDescription: LiveData<PokemonDescription> = _pokemonDescription

    private val _pokemonEvolutionChain = MutableLiveData<List<Pokemon>>()
    val pokemonEvolutionChain : LiveData<List<Pokemon>> = _pokemonEvolutionChain

    fun getPokemonDescription(pokemonId:Int){
        viewModelScope.launch {
            val pokemonDesc = getPokemonDescriptionUseCase.invoke(pokemonId)
            _pokemonDescription.postValue(pokemonDesc)
        }
    }

    fun getPokemonEvolutionChain(pokemonEvolutionUrl:String){
        viewModelScope.launch {
            val pokemonEvolutionChain = getEvolutionChainUseCase.invoke(pokemonEvolutionUrl)
            Log.d("evolution", pokemonEvolutionChain.toString())
            _pokemonEvolutionChain.postValue(pokemonEvolutionChain)
        }
    }

}