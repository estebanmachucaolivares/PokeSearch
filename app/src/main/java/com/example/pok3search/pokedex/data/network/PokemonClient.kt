package com.example.pok3search.pokedex.data.network


import com.example.pok3search.pokedex.data.network.response.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import retrofit2.Call
import retrofit2.http.Path

interface PokemonClient {

    @GET("generation")
    suspend fun getAllGenerations(): GenerationListResponse

    @GET
    suspend fun getGeneration(@Url url: String): GenerationDetailResponse

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonDetails(@Path("id") id: Int):PokemonDescriptionResponse
    @GET("pokemon-species/{id}/")
    suspend fun getPokemonEvolutionChainUrl(@Path("id") id: Int):PokemonEvolutionsResponse


    @GET
    fun getPokemonEvolutionChain(@Url url: String): Call<EvolutionChain>

    @GET("pokemon/{id}")
    suspend fun getPokemonStats(@Path("id") id: Int):PokemonStatsResponse


    @GET("pokemon/{id}")
    suspend fun getPokemonAbilityUrls(@Path("id") id: Int):PokemonAbilityUrlResponse

    @GET
    suspend fun getPokemonAbility(@Url url: String): PokemonAbilityResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonTypesUrls(@Path("id") id: Int):PokemonTypeUrlResponse

    @GET
    suspend fun getPokemonTypes(@Url url: String): PokemonTypesName
}
