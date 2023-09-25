package com.example.pok3search.pokedex.data.network.response

data class PokemonDescriptionResponse(val flavor_text_entries:List<PokemonDescriptionItemsResponse>, val genera:List<PokemonGeneraItemsResponse>)

data class PokemonDescriptionItemsResponse(val flavor_text:String, val language:PokemonLanguage)

data class PokemonGeneraItemsResponse(val genus: String, val language:PokemonLanguage)
