package com.example.pok3search.pokedex.data.network

import com.example.pok3search.pokedex.data.network.response.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonClient {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
        //@Query("offset") offset: Int
    ): PokemonListResponse
}
