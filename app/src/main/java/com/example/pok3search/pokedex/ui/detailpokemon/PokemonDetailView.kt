package com.example.pok3search


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.pok3search.pokedex.domain.model.*
import com.example.pok3search.pokedex.ui.detailpokemon.DetailPokemonViewModel
import com.example.pok3search.ui.theme.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetail(
     pokemon: Pokemon,
     navigationController: NavHostController,
     detailPokemonViewModel: DetailPokemonViewModel
) {

     DisposableEffect(Unit) {
          onDispose {
               // Llama a la función para limpiar los datos en el ViewModel
               detailPokemonViewModel.clearPokemonDetail()
          }
     }

     val composition by rememberLottieComposition(
          spec = LottieCompositionSpec.RawRes(R.raw.animation_background)// Reemplaza con la referencia a tu archivo JSON
     )

     val pokemonDescription:PokemonDescription by detailPokemonViewModel.pokemonDescription.observeAsState(initial = PokemonDescription(0,"",""))
     val pokemonEvolutionChain:List<PokemonEvolutionChain> by detailPokemonViewModel.pokemonEvolutionChain.observeAsState(initial = listOf())
     val pokemonStats:PokemonStats by detailPokemonViewModel.pokemonStats.observeAsState(initial = PokemonStats())
     val pokemonAbilities:List<PokemonAbility> by detailPokemonViewModel.pokemonAbilities.observeAsState(initial = listOf())
     val pokemonTypes:List<PokemonTypes> by detailPokemonViewModel.pokemonTypes.observeAsState(initial = listOf())

     //Obtener información de Pokemon
     detailPokemonViewModel.getPokemonDescription(pokemon.id)
     detailPokemonViewModel.getPokemonType(pokemon.id)
     detailPokemonViewModel.getPokemonEvolutionChain(pokemon.id)
     detailPokemonViewModel.getPokemonAbility(pokemon.id)
     detailPokemonViewModel.getPokemonStats(pokemon.id)

     Scaffold(
          topBar = {
               TopBar(pokemon.name,navigationController)
          },
          containerColor = Color.Transparent,
          content = {


               Column(
                    modifier = Modifier
                         .fillMaxSize()
                         .padding(top = 65.dp)
                         .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
               ) {

                    MainImage(pokemon, composition)

                    TypeChips(pokemonTypes)

                    Description(pokemonDescription)

                    EvolutionChain(pokemonEvolutionChain)

                    Stats(
                         pokemonStats.hp,
                         pokemonStats.attack,
                         pokemonStats.defense,
                         pokemonStats.speed,
                         pokemonStats.specialAttack,
                         pokemonStats.specialDefense
                    )

                    Abilities(pokemonAbilities)
               }
          })
}

@Composable
fun TopBar(pokemonName: String, navigationController: NavHostController){

     CenterAlignedTopAppBar(
          title = {
               Text(
                    pokemonName.replaceFirstChar { it.uppercase() },
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
private fun MainImage(
     pokemon: Pokemon,
     composition: LottieComposition?
) {
     Box(contentAlignment = Alignment.Center) {

          Image(
               painter = painterResource(id = R.drawable.pokemon_back),
               contentDescription = null,
               modifier = Modifier
                    .align(Alignment.Center)
                    .size(250.dp),
               contentScale = ContentScale.Fit
          )

          val painter =
               rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")

          LottieAnimation(
               composition = composition,
               iterations = LottieConstants.IterateForever,
               modifier = Modifier.size(300.dp)
          )

          if (painter.state is AsyncImagePainter.State.Error || painter.state is AsyncImagePainter.State.Empty) {
               Image(
                    painter = painterResource(id = R.drawable.pokeball_default_img),
                    contentDescription = null,
                    modifier = Modifier
                         .align(Alignment.Center)
                         .padding(bottom = 10.dp)
                         .fillMaxSize()
                         .size(150.dp),
                    contentScale = ContentScale.Fit
               )
          } else {
               Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                         .align(Alignment.Center)
                         .padding(bottom = 10.dp)
                         .fillMaxSize()
                         .size(200.dp),
                    contentScale = ContentScale.Fit
               )
          }

     }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TypeChips(pokemonTypes: List<PokemonTypes>) {
     Row(
          modifier = Modifier
               .fillMaxWidth()
               .height(35.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
     ) {

          pokemonTypes.forEach {
               val colorChips = colorByType(it.typeName)
               AssistChip(
                    modifier = Modifier.padding(4.dp),
                    label = { Text(text = it.typeName) },
                    onClick = {},
                    colors = AssistChipDefaults.assistChipColors(
                         labelColor = Color.White,
                         containerColor = colorChips
                    ),
                    border = AssistChipDefaults.assistChipBorder(
                         borderColor = colorChips
                    )
               )
          }
     }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Description(pokemonDescription: PokemonDescription) {
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
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EvolutionChain(pokemonEvolutionChain: List<PokemonEvolutionChain>) {
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
                    itemsIndexed(pokemonEvolutionChain) { index, item ->
                         EvolutionItem(item.pokemon, index == pokemonEvolutionChain.lastIndex)
                    }
               }
          }
     }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Stats(
     hp: Int,
     attack: Int,
     defense: Int,
     speed: Int,
     specialAttack: Int,
     specialDefense: Int
) {
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
                         text = hp.toString(),
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
                         progress = statsToFloat(hp)
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
                         text = attack.toString(),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.weight(0.1f),
                         color = Color.Black,
                         fontSize = 14.sp
                    )
                    LinearProgressIndicator(
                         modifier = Modifier
                              .size(width = 100.dp, height = 5.dp)
                              .weight(0.6f),
                         progress = statsToFloat(attack)
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
                         text = defense.toString(),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.weight(0.1f),
                         color = Color.Black,
                         fontSize = 14.sp
                    )
                    LinearProgressIndicator(
                         modifier = Modifier
                              .size(width = 100.dp, height = 5.dp)
                              .weight(0.6f),
                         progress = statsToFloat(defense)
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
                         text = speed.toString(),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.weight(0.1f),
                         color = Color.Black,
                         fontSize = 14.sp
                    )
                    LinearProgressIndicator(
                         modifier = Modifier
                              .size(width = 100.dp, height = 5.dp)
                              .weight(0.6f),
                         progress = statsToFloat(speed)
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
                         text = specialAttack.toString(),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.weight(0.1f),
                         color = Color.Black,
                         fontSize = 14.sp
                    )
                    LinearProgressIndicator(
                         modifier = Modifier
                              .size(width = 100.dp, height = 5.dp)
                              .weight(0.6f),
                         progress = statsToFloat(specialAttack)
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
                         text = specialDefense.toString(),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.weight(0.1f),
                         color = Color.Black,
                         fontSize = 14.sp
                    )
                    LinearProgressIndicator(
                         modifier = Modifier
                              .size(width = 100.dp, height = 5.dp)
                              .weight(0.6f),
                         progress = statsToFloat(specialDefense)
                    )
               }
          }
     }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Abilities(pokemonAbilities: List<PokemonAbility>) {
     Card(
          modifier = Modifier
               .padding(10.dp)
               .fillMaxWidth(),

          colors = CardDefaults.cardColors(containerColor = Color.White)
     ) {
          Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier.padding(20.dp),
               verticalArrangement = Arrangement.Center
          ) {
               Text(
                    text = "Habilidades",
                    Modifier.padding(bottom = 20.dp),
                    color = textItemColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
               )

               pokemonAbilities.forEach {
                    PokemonAbilityItem(it)
               }
          }
     }
}

@Composable
fun EvolutionItem(pokemon:Pokemon, isEnd: Boolean){
     val painter = rememberAsyncImagePainter("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png")

     Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(bottom = 20.dp)
     ){
          Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

               if(painter.state is AsyncImagePainter.State.Error || painter.state is AsyncImagePainter.State.Empty){
                    Image(
                         painter = painterResource(id = R.drawable.pokeball_default_img),
                         contentDescription = null,
                         modifier = Modifier
                              .padding(bottom = 5.dp)
                              .size(60.dp),
                         contentScale = ContentScale.Fit
                    )
               }else{
                    Image(
                         painter = painter,
                         contentDescription = null,
                         modifier = Modifier
                              .padding(bottom = 5.dp)
                              .size(60.dp),
                         contentScale = ContentScale.Fit
                    )
               }

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



/**
 * Convierte un valor entero en un valor de punto flotante.
 *
 * Esta función toma un valor entero `stats`, lo divide por 100 y devuelve un valor de punto flotante.
 * Si el valor resultante es mayor que 1, se ajusta a 1. Si ocurre alguna excepción durante este proceso,
 * la función devuelve 0.
 *
 * @param stats El valor entero de estadísticas que se va a convertir.
 * @return El valor de punto flotante resultante, limitado en el rango [0, 1].
 */
private fun statsToFloat(stats: Int): Float {
     return try {
          val statsFloat = stats.toFloat() / 100
          if (statsFloat > 1) 1f else statsFloat
     } catch (e: Exception) {
          0f
     }
}

@Composable
fun PokemonAbilityItem(pokemonAbility: PokemonAbility){
     Column(modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 10.dp)) {
          Text(
               text = pokemonAbility.name,
               color = textItemColor,
               textAlign = TextAlign.Start,
               fontSize = 14.sp
          )
          Text(
               text = pokemonAbility.description,
               textAlign = TextAlign.Start,
               color = Color.Black,
               fontSize = 14.sp
          )
     }
}



/**
 * Devuelve un color basado en el tipo de Pokémon proporcionado.
 *
 * @param type tipo de Pokémon como una cadena (por ejemplo, "agua").
 * @return El color asociado con el tipo de Pokémon, o el color predeterminado si el tipo es desconocido.
 */
fun colorByType(type:String): Color{
     val colorByType = mapOf(
          "normal" to colorNormal,
          "lucha" to colorLucha,
          "volador" to colorVolador,
          "veneno" to colorVeneno,
          "tierra" to colorTierra,
          "roca" to colorRoca,
          "bicho" to colorBicho,
          "fantasma" to colorFantasma,
          "acero" to colorAcero,
          "fuego" to colorFuego,
          "agua" to colorAgua,
          "planta" to colorPlanta,
          "eléctrico" to colorElectrico,
          "psíquico" to colorPsiquico,
          "hielo" to colorHielo,
          "dragón" to colorDragon,
          "siniestro" to colorSiniestro,
          "hada" to colorHada,
          "desconocido" to colorDesconocido,
          "sombra" to colorSombra
     )
     return colorByType[type.lowercase()] ?: textItemColor
}


