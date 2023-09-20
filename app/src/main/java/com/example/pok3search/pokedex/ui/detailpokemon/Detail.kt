package com.example.pok3search


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pok3search.pokedex.ui.detailpokemon.DetailPokemonViewModel

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import com.example.pok3search.ui.theme.shadowBack

@Preview
@Composable
fun PokemonDetail(){
     val radiusDp = 100.dp
     val density = LocalDensity.current.density
     val radiusPx = with(LocalDensity.current) { radiusDp.toPx() / density }

     Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
          Box(Modifier.size(200.dp), contentAlignment = Alignment.Center) {
               Image(
                    painter = painterResource(id = R.drawable.pokemon_back),
                    contentDescription = null,
                    modifier = Modifier
                         .align(Alignment.Center)
                         .fillMaxSize(),
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
                         .padding(bottom = 10.dp)
                         .fillMaxSize()
                    ,
                    contentScale = ContentScale.Fit
               )

          }
     }
}