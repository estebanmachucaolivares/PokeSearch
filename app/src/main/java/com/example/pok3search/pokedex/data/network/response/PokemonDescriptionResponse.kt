package com.example.pok3search.pokedex.data.network.response

data class PokemonDescriptionResponse(val flavor_text_entries:List<PokeomDescriptionItemsResponse>, val genera:List<PokemonGeneraItemsResponse>, val evolution_chain:PokemonEvolutionUrlResponse)