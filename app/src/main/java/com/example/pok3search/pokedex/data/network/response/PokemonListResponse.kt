package com.example.pok3search.pokedex.data.network.response

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonListItemResponse>
)