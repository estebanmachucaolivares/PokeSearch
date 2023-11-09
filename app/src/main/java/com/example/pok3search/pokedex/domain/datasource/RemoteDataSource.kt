package com.example.pok3search.pokedex.domain.datasource

import com.example.pok3search.pokedex.domain.model.*

interface RemoteDataSource: BaseDataSource {
    suspend fun getAllPokemonWithRegion(): List<PokemonGroupByRegion>
}