package com.example.pok3search.pokedex.data.network.response

import com.google.gson.annotations.SerializedName

data class PokemonSpecies(@SerializedName("generation") val generation: PokemonGenerationResult)
