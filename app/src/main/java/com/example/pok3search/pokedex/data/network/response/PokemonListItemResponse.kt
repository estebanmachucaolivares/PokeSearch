package com.example.pok3search.pokedex.data.network.response

import com.google.gson.annotations.SerializedName

data class PokemonListItemResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)