package com.example.pok3search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import com.example.pok3search.pokedex.ui.listpokemons.ListPokemonViewModel
import com.example.pok3search.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(listPokemonViewModel: ListPokemonViewModel){

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ },
                contentColor = Color.White,
                containerColor = detailBackground
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "")
            }
        },
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            listPokemonViewModel.getAllPokemons()
            val pokemonList:List<PokemonGroupByRegion> by listPokemonViewModel.pokemonList.observeAsState(initial = listOf())
            var searchText by remember { mutableStateOf("") }

            SearchBar(
                modifier = Modifier.padding(10.dp),
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchSubmit = { /* Handle search submit here */ },
                onClearSearch = { searchText = "" }
            )

            RegionChips(pokemonList)

            PokemonGridList(pokemonList)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionChips(regionList:List<PokemonGroupByRegion>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(regionList) { region ->
            AssistChip(
                modifier = Modifier.padding(4.dp),
                label = { Text(text = region.region) },
                onClick = { /* Maneja el clic en el chip si es necesario */ },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = Color.Black
                )
            )
        }
    }
}

@Composable
fun PokemonItem(index: Int, pokemon: Pokemon){
    val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png")

    val radiusDp = 100.dp
    val density = LocalDensity.current.density
    val radiusPx = with(LocalDensity.current) { radiusDp.toPx() / density }

    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.pokemon_back),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(shadowBack, Color.Transparent),
                            radius = radiusPx, // El radio del degradado en píxeles
                            tileMode = TileMode.Clamp
                        )
                    )
                    .align(Alignment.Center)
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 5.dp)
                    .size(60.dp),
                contentScale = ContentScale.Fit
            )


        }
        val pokemonText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("#$index ")
            }
            append(pokemon.name)
        }

        Text(
            text = pokemonText,
            color = textItemColor,
            fontSize = 12.sp
        )
    }

}

@Composable
fun PokemonGridList(pokemonList: List<PokemonGroupByRegion>) {

    Log.d("pokemon list agrupada", pokemonList.toString())


    // Controla si el FAB debe estar visible
    var isFabVisible by remember { mutableStateOf(false) }

    // Crea un controlador de desplazamiento para LazyVerticalGrid
    val scrollState = rememberLazyListState()


    val listState = rememberLazyGridState()

    Log.d("scroll","liststate ${listState.firstVisibleItemIndex}")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {
            pokemonList.forEach { (region, pokemonInRegion) ->

                header{
                    Text(
                        text = region,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textTitleRegionColor,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                // Elementos de Pokémon de la región
                itemsIndexed(pokemonInRegion) { index, pokemon ->
                    PokemonItem(pokemon.id, pokemon)
                }
            }
        },
        state = listState

    )
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PokemonView(){
    val radiusDp = 100.dp
    val density = LocalDensity.current.density
    val radiusPx = with(LocalDensity.current) { radiusDp.toPx() / density }

    Column( horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.pokemon_back),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(shadowBack, Color.Transparent),
                            center = Offset(110f, 110f), // El centro del degradado
                            radius = radiusPx, // El radio del degradado en píxeles
                            tileMode = TileMode.Clamp
                        )
                    )
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.pokemon_test_view),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 5.dp)
                    .size(60.dp)
                    ,
                contentScale = ContentScale.Fit
            )



        }
        val pokemonText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("#1 ")
            }
            append("Bulbasaur")
        }

        Text(
            text = pokemonText,
            color = textItemColor,
            fontSize = 12.sp
        )
    }


}