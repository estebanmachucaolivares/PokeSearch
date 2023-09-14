package com.example.pok3search.pokedex.data.network.response

import com.google.gson.annotations.SerializedName

data class GenerationResponse(@SerializedName("main_region") val main_region: RegionResponse)