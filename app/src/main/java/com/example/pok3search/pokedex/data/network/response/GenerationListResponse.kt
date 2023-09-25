package com.example.pok3search.pokedex.data.network.response

data class GenerationListResponse(val results: List<GenerationItemsResponse>)

data class GenerationItemsResponse(val name: String, val url: String)
