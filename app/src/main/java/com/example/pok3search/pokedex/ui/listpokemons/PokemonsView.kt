package com.example.pok3search

import SearchBar
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonGroupByRegion
import com.example.pok3search.pokedex.ui.PokemonWithRegionUiState
import com.example.pok3search.pokedex.ui.listpokemons.ListPokemonViewModel
import com.example.pok3search.ui.theme.*
import kotlinx.coroutines.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(listPokemonViewModel: ListPokemonViewModel, navigationController: NavHostController){

    var showFab by remember { mutableStateOf(false) }

    val pokemonListState = rememberLazyGridState()

    val regionListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val selectedPosition = remember { mutableStateOf(0) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    listPokemonViewModel.getAllPokemon()

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_background)
    )

    val pokemonWithRegionUiState by produceState<PokemonWithRegionUiState>(initialValue = PokemonWithRegionUiState.Loading,
        key1 = lifecycle,
        key2 =listPokemonViewModel
    ){
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            listPokemonViewModel.pokemonWithRegionUiState.collect{
                value =it
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if(showFab){
                FloatingActionButton(onClick = {
                    coroutineScope.launch{
                        pokemonListState.scrollToItem(0)
                        regionListState.animateScrollToItem(0)
                    }
                    selectedPosition.value = 0
                },
                    contentColor = Color.White,
                    containerColor = detailBackground
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = "")
                }
            }
        },
        containerColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){

            var searchText by remember { mutableStateOf("") }

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

            when(pokemonWithRegionUiState){
                is PokemonWithRegionUiState.Error -> {}
                PokemonWithRegionUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }

                is PokemonWithRegionUiState.Success -> {

                     val pokemonList:List<PokemonGroupByRegion> = (pokemonWithRegionUiState as PokemonWithRegionUiState.Success).pokemonWithRegion

                    val filteredPokemonList = remember { mutableStateOf(pokemonList) }

                    LaunchedEffect(pokemonList) {
                        if(pokemonList.isNotEmpty()){
                            filteredPokemonList.value = pokemonList
                        }
                    }

                    SearchBar(
                        modifier = Modifier.padding(10.dp),
                        searchText = searchText,
                        onSearchTextChanged = {
                            searchText = it
                            filterPokemonList(pokemonList, it, filteredPokemonList)
                        },
                        onSearchSubmit = { Log.d("search", "submit") },
                        onClearSearch = {
                            searchText = ""
                            filterPokemonList(pokemonList, "", filteredPokemonList)
                        }
                    )

                    RegionChips(pokemonList,selectedPosition, regionListState) { newPosition ->
                        coroutineScope.launch {
                            pokemonListState.scrollToItem(newPosition)
                            //listState.animateScrollToItem(newPosition) TODO validar index actual y de destino para un maximo de 50 items para animar el scroll
                        }
                    }

                    PokemonGridList(filteredPokemonList, pokemonListState,navigationController){
                        showFab = it
                    }

                }
                else -> {}
            }
        }
    }
}


/**
 * Función para filtrar lista pokemonList
 * @param pokemonList lista completa para filtrar
 * @param filterText texto a buscar en la lista
 * @param filteredPokemonList lista con filtor aplicado
 */
fun filterPokemonList(
    pokemonList: List<PokemonGroupByRegion>,
    filterText: String,
    filteredPokemonList: MutableState<List<PokemonGroupByRegion>>
) {
    val lowercaseFilterText = filterText.lowercase()

    val filteredList = if (lowercaseFilterText.isEmpty()) {
        // Si el campo de búsqueda está vacío, mostrar la lista completa
        pokemonList
    } else {
        // Filtrar por nombre de Pokémon o ID en cada región
        pokemonList.map { region ->
            val filteredRegion = region.copy(
                pokemonList = region.pokemonList.filter { pokemon ->
                    val nameMatches = pokemon.name.lowercase().contains(lowercaseFilterText)
                    val idMatches = pokemon.id.toString().contains(lowercaseFilterText)
                    nameMatches || idMatches
                }
            )
            filteredRegion
        }
    }

    filteredPokemonList.value = filteredList
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionChips(
    regionList: List<PokemonGroupByRegion>,
    selectedPosition: MutableState<Int>,
    listState: LazyListState,
    onRegionClick: (Int) -> Unit
) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = listState
    ) {
        itemsIndexed(regionList) { index,region ->
            val isSelected = index == selectedPosition.value
            AssistChip(
                modifier = Modifier.padding(4.dp),
                label = { Text(text = region.region) },
                onClick = {

                    selectedPosition.value = index
                    // Encuentra el índice correcto de la región al hacer clic en el chip
                    val startIndex = regionList.subList(0, index).sumBy { it.pokemonList.size } + index

                    onRegionClick(startIndex)

                },
                colors = AssistChipDefaults.assistChipColors(
                    labelColor = if (isSelected) Color.White else textItemColor,
                    containerColor = if (isSelected) detailBackground else Color.Transparent
                )
            )
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, onItemSelected: (Pokemon) ->Unit){
    val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")

    val radiusDp = 100.dp
    val density = LocalDensity.current.density
    val radiusPx = with(LocalDensity.current) { radiusDp.toPx() / density }

    Column( horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        onItemSelected(pokemon)
    }) {
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

            if(painter.state is AsyncImagePainter.State.Error || painter.state is AsyncImagePainter.State.Empty){
                Image(
                    painter = painterResource(id = R.drawable.pokeball_default_img),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp),
                    contentScale = ContentScale.Fit
                )
            }else{
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



        }
        val pokemonText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("#${pokemon.id} ")
            }
            append(pokemon.name.replaceFirstChar { it.uppercase() })
        }

        Text(
            text = pokemonText,
            color = textItemColor,
            fontSize = 12.sp
        )
    }

}

@Composable
fun PokemonGridList(
    pokemonList: MutableState<List<PokemonGroupByRegion>>,
    listState: LazyGridState,
    navigationController: NavHostController,
    showFab: (Boolean) -> Unit
) {

    showFab(listState.firstVisibleItemIndex > 2)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = {

            var currentIndex = 0

            pokemonList.value.forEach { (region, pokemonInRegion) ->
                if(pokemonInRegion.isNotEmpty()){
                    // headers de regiones
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

                        currentIndex++

                        // Verifica si este elemento es el que se debe desplazar
                        if (currentIndex - 1 == listState.firstVisibleItemIndex) {
                            // Notifica la nueva posición
                            val newPosition = currentIndex - 1
                            // Solo notificamos la nueva posición si la posición cambia
                            if (newPosition != listState.firstVisibleItemIndex) {

                                GlobalScope.launch{
                                    withContext(Dispatchers.Main){
                                        listState.scrollToItem(newPosition)
                                    }
                                }

                            }
                        }

                        PokemonItem(pokemon){
                            navigationController.navigate("Pokemondetail/${it.id}/${it.name}")
                        }
                    }
                }
            }
        },
        state = listState

    )
}


/**
 * Función de extensión LazyGridScope para agregar un header un un LazyGrid
 */
fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}