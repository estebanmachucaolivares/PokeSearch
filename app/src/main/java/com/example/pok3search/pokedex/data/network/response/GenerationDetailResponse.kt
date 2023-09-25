package com.example.pok3search.pokedex.data.network.response

data class GenerationDetailResponse(val main_region: RegionResponse, val pokemon_species: List<PokemonListItemResponse> )

data class RegionResponse(val name: String)



