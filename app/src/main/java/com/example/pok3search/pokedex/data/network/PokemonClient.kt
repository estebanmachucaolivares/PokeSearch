package com.example.pok3search.pokedex.data.network


import com.example.pok3search.pokedex.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.Call
import retrofit2.http.Path

interface PokemonClient {

    @GET("pokemon-species/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int
        //@Query("offset") offset: Int
    ): PokemonListResponse

    @GET("generation")
    suspend fun getAllGenerations(): GenerationListResponse

    @GET
    suspend fun getGeneration(@Url url: String): GenerationDetailResponse

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonDetails(@Path("id") id: Int):PokemonDescriptionResponse

    @GET
    fun getEvolutionChain(@Url url: String): Call<EvolutionChain>

    @GET("pokemon/{id}")
    suspend fun getPokemonStats(@Path("id") id: Int):PokemonStatsResponse
}
