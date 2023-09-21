package com.example.pok3search.pokedex.ui.detailpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pok3search.pokedex.domain.GetPokemonDescriptionUseCase
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(private val getPokemonDescriptionUseCase: GetPokemonDescriptionUseCase): ViewModel() {

    private val _pokemonDescription = MutableLiveData<PokemonDescription>()

    val pokemonDescription: LiveData<PokemonDescription> = _pokemonDescription

    fun getPokemonDescription(pokemonId:Int){
        viewModelScope.launch {
            val pokemonDesc = getPokemonDescriptionUseCase.invoke(pokemonId)
            _pokemonDescription.postValue(pokemonDesc)
        }
    }

}