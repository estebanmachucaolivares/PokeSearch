package com.example.pok3search.pokedex.data.network


import com.example.pok3search.pokedex.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.Call

interface PokemonClient {

    @GET("pokemon-species/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
        //@Query("offset") offset: Int
    ): PokemonListResponse


    @GET("pokemon-species/")
    suspend fun getAllPokemonSpecies(): PokemonListResponse

    @GET
    suspend fun getPokemonSpecies(@Url url: String):PokemonSpecies

    @GET
    suspend fun getGenerationByUrl(@Url url: String): GenerationResponse

}
