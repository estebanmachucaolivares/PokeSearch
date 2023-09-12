package com.example.pok3search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pok3search.pokedex.ui.listpokemons.ListPokemonViewModel
import com.example.pok3search.ui.theme.mainBackgroundColor

@Composable
fun MainScreen(listPokemonViewModel: ListPokemonViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
    ){

        listPokemonViewModel.getAllPokemons()

        Column() {
            Box(
                modifier = Modifier
            ){

                Box(  modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .widthIn(max = Dp.Infinity)){
                    Image(
                        painter = painterResource(id = R.drawable.circle_dex),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopStart),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(  modifier = Modifier
                    .widthIn(max = Dp.Infinity)){
                    Image(
                        painter = painterResource(id = R.drawable.line_top_dex),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopStart),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            var searchText by remember { mutableStateOf("") }

            SearchBar(
                modifier = Modifier.padding(10.dp),
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchSubmit = { /* Handle search submit here */ },
                onClearSearch = { searchText = "" }
            )
        }

    }
}