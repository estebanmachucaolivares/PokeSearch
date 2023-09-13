package com.example.pok3search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.ui.listpokemons.ListPokemonViewModel
import com.example.pok3search.ui.theme.mainBackgroundColor
import com.example.pok3search.ui.theme.textItemColor

@Composable
fun MainScreen(listPokemonViewModel: ListPokemonViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainBackgroundColor)
    ){


        listPokemonViewModel.getAllPokemons()
        val pokemonList:List<Pokemon> by listPokemonViewModel.pokemonList.observeAsState(initial = listOf())

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

            pokemonGridList(pokemonList)
        }

    }
}

@Composable
fun pokemonItem(index: Int,pokemon: Pokemon){
    val pokemonNumber = index+1
    val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonNumber.png")



    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.pokemon_base),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Fit
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(bottom = 5.dp)
                    .size(60.dp),
                contentScale = ContentScale.Fit
            )


        }
        Text(text = "#$pokemonNumber ${pokemon.name}", color = textItemColor, fontSize = 12.sp)
    }

}

@Composable
fun pokemonGridList(pokemonList: List<Pokemon>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        content = {
            itemsIndexed(pokemonList) { index, pokemon ->
                pokemonItem(index, pokemon)
            }
        })
}