package com.example.pok3search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pok3search.ui.theme.*


@Preview
@Composable
fun MainScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
    ){
        Text("main Screen")
    }
}

@Composable
fun PokemonDetail(){

}