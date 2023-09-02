package com.example.pok3search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import com.example.pok3search.ui.theme.pokeRed


@Composable
fun MainScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){
        Text(text = "Pantalla 1")
    }
}

@Composable
fun SearchScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){
        Text(text = "Pantalla 2")
    }
}

@Composable
fun Pokemondetail(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){
        Text(text = "Pantalla 3")
    }
}