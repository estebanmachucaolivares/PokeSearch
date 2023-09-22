package com.example.pok3search


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.domain.model.PokemonDescription
import com.example.pok3search.pokedex.ui.detailpokemon.DetailPokemonViewModel
import com.example.pok3search.ui.theme.Primary
import com.example.pok3search.ui.theme.textItemColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetail(
     pokemon: Pokemon,
     navigationController: NavHostController,
     detailPokemonViewModel: DetailPokemonViewModel
) {

     detailPokemonViewModel.getPokemonDescription(pokemonId = pokemon.id)

     val pokemonDescription:PokemonDescription by detailPokemonViewModel.pokemonDescription.observeAsState(initial = PokemonDescription("","",""))

     val pokemonEvolutionChain:List<Pokemon> by detailPokemonViewModel.pokemonEvolutionChain.observeAsState(initial = listOf())

     LaunchedEffect(pokemonDescription){
         if(pokemonDescription.pokemonEvolutionUrl.isNotEmpty()){
              detailPokemonViewModel.getPokemonEvolutionChain(pokemonDescription.pokemonEvolutionUrl)
         }
     }

     Scaffold(
          topBar = {
               TopBar(pokemon.name,navigationController)
          },
          containerColor = Color.Transparent,
          content = {

               val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")

               Column(
                    modifier = Modifier
                         .fillMaxSize()
                         .padding(top = 65.dp)
                         .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
               ) {

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

                    Box(Modifier.size(200.dp), contentAlignment = Alignment.Center) {
                         Image(
                              painter = painter,
                              contentDescription = null,
                              modifier = Modifier
                                   .align(Alignment.Center)
                                   .padding(bottom = 10.dp)
                                   .fillMaxSize(),
                              contentScale = ContentScale.Fit
                         )
                    }

                    Card(
                         modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth(),

                         colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                         Column(
                              modifier = Modifier.fillMaxWidth(),
                              horizontalAlignment = Alignment.CenterHorizontally,
                              verticalArrangement = Arrangement.Center
                         ) {
                              Text(
                                   text = pokemonDescription.pokemonType,
                                   Modifier.padding(20.dp),
                                   color = textItemColor,
                                   fontWeight = FontWeight.Bold,
                                   textAlign = TextAlign.Center
                              )
                              Text(
                                   text = pokemonDescription.pokemonDescription,
                                   Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                                   textAlign = TextAlign.Center,
                                   color = Color.Black,
                                   fontSize = 14.sp
                              )
                         }
                    }


                    if(pokemonEvolutionChain.size>1){
                         Card(
                              modifier = Modifier
                                   .padding(10.dp)
                                   .fillMaxWidth(),

                              colors = CardDefaults.cardColors(containerColor = Color.White)
                         ) {

                              Column(
                                   modifier = Modifier.fillMaxWidth(),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = Arrangement.Center
                              ) {
                                   Text(
                                        text = "Evoluciones",
                                        Modifier.padding(20.dp),
                                        color = textItemColor,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                   )

                                   LazyRow(
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(bottom = 20.dp)
                                   ) {
                                        itemsIndexed(pokemonEvolutionChain) { index,item ->
                                             EvolutionIndex(item,index == pokemonEvolutionChain.lastIndex)
                                        }
                                   }
                              }
                         }
                    }



                    Card(
                         modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth(),

                         colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                         Column(
                              horizontalAlignment = Alignment.CenterHorizontally,
                              modifier = Modifier.padding(20.dp)
                         ) {
                              Text(
                                   text = "Estadísticas",
                                   Modifier.padding(bottom = 20.dp),
                                   color = textItemColor,
                                   fontWeight = FontWeight.Bold,
                                   textAlign = TextAlign.Center
                              )


                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "PS",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(
                                                  width = 100.dp,
                                                  height = 5.dp
                                             )
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "Ataque",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(width = 100.dp, height = 5.dp)
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "Defensa",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(width = 100.dp, height = 5.dp)
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "Velocidad",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(width = 100.dp, height = 5.dp)
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "At. Especial",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(width = 100.dp, height = 5.dp)
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                              Row(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 3.dp),
                                   horizontalArrangement = Arrangement.SpaceBetween,
                                   verticalAlignment = Alignment.CenterVertically
                              ) {
                                   Text(
                                        text = "Def. Especial",
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier.weight(0.3f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "45",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.weight(0.1f),
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                                   LinearProgressIndicator(
                                        modifier = Modifier
                                             .size(width = 100.dp, height = 5.dp)
                                             .weight(0.6f),
                                        progress = 0.5f
                                   )
                              }
                         }
                    }

                    Card(
                         modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth(),

                         colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                         Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(20.dp)) {
                              Text(
                                   text = "Habilidades",
                                   Modifier.padding(bottom = 20.dp),
                                   color = textItemColor,
                                   fontWeight = FontWeight.Bold,
                                   textAlign = TextAlign.Center
                              )
                              Column(modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(bottom = 10.dp)) {
                                   Text(
                                        text = "Espesura",
                                        color = textItemColor,
                                        textAlign = TextAlign.Start,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "Potencia los ataques de tipo\n" +
                                                "planta en un apuro.",
                                        textAlign = TextAlign.Start,
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                              }

                              Column(modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(bottom = 10.dp)) {
                                   Text(
                                        text = "Espesura",
                                        color = textItemColor,
                                        textAlign = TextAlign.Start,
                                        fontSize = 14.sp
                                   )
                                   Text(
                                        text = "Potencia los ataques de tipo\n" +
                                                "planta en un apuro.",
                                        textAlign = TextAlign.Start,
                                        color = Color.Black,
                                        fontSize = 14.sp
                                   )
                              }
                         }
                    }
               }
          })
}

@Composable
fun TopBar(pokemonName: String, navigationController: NavHostController){

     CenterAlignedTopAppBar(
          title = {
               Text(
                    pokemonName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
               )
          },
          navigationIcon = {
               IconButton(onClick = {
                    navigationController.popBackStack()
               }) {
                    Icon(
                         imageVector = Icons.Filled.ArrowBack,
                         contentDescription = "Back"
                    )
               }
          },
          actions = {
              
          },
          colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
               containerColor = Primary,
               titleContentColor = Color.White,
               navigationIconContentColor = Color.White
          )
     )
}

@Composable
fun EvolutionIndex(pokemon:Pokemon, isEnd: Boolean){
     val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")

     Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(bottom = 20.dp)
     ){
          Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
               Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                         .padding(bottom = 5.dp)
                         .size(60.dp),
                    contentScale = ContentScale.Fit
               )
               Text(
                    text = pokemon.name,
                    color = textItemColor,
                    fontSize = 12.sp
               )
          }
          if (!isEnd){
               Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = ""
               )
          }
     }
}