package com.example.pok3search.pokedex.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val isSuccess get() = this is Success
}