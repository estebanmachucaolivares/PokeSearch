package com.example.pok3search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pok3search.ui.theme.borderStrokeScreenColor
import com.example.pok3search.ui.theme.pokeRed
import com.example.pok3search.ui.theme.screenColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pokemondetail(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){
        Card(
            colors =  CardDefaults.cardColors(
                containerColor = screenColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            border = BorderStroke(1.dp, borderStrokeScreenColor)

        ) {

            Box(Modifier.fillMaxSize().padding(10.dp).background(Color.White)){

                Image(
                    painter = painterResource(id = R.drawable.pasto),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Crop// Puedes cambiar el color de fondo si lo deseas
                )

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.4f) // 40% de la pantalla
                    ) {
                        // Contenido del primer Row (40% de la pantalla)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.6f) // 60% de la pantalla
                    ) {
                        Card(
                            colors =  CardDefaults.cardColors(
                                containerColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxSize().padding(10.dp)
                        ){

                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pokeRed)
    ){

    }
}