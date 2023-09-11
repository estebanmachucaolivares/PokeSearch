package com.example.pok3search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pok3search.ui.theme.*



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {

    val navigationController = rememberNavController()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ },
                    contentColor = Color.White,
                containerColor = detailBackground
                ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "")
            }
        },
        containerColor = mainBackgroundColor
    ) {
        NavHost(navController = navigationController, startDestination = "MainScreen") {
            composable("MainScreen") {
                MainScreen()
            }
            composable("Pokemondetail") {
                PokemonDetail()
            }

        }
    }
}

//navigationController.navigate("MainScreen")
//navigationController.navigate("Pokemondetail")
