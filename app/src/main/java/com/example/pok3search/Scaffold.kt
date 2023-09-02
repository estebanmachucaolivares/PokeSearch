package com.example.pok3search

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pok3search.ui.theme.iconUnselected
import com.example.pok3search.ui.theme.pokeRed
import com.example.pok3search.ui.theme.pokeRedSelected

@Preview(showBackground = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(){
    Scaffold(
        bottomBar = {MainBottomBar()}
    ) {
        val navigationController = rememberNavController()
        NavHost(navController = navigationController, startDestination = "MainScreen"){
            composable("MainScreen") { MainScreen()}
            composable("SearchScreen") { SearchScreen()}
            composable("Pokemondetail") { Pokemondetail()}
        }
    }
}



@Composable
fun MainBottomBar(){
    var index by rememberSaveable{ mutableStateOf(0) }
    val mainBottomBarColor = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = iconUnselected,
        selectedTextColor = Color.White,
        indicatorColor = pokeRedSelected,
        unselectedTextColor = iconUnselected
    )

    NavigationBar(containerColor = pokeRed) {
        NavigationBarItem(
            icon = {  Icon(Icons.Filled.List, contentDescription = "prueba") },
            label = { Text("Pokemones") },
            selected = index == 0,
            onClick = {index = 0},
            colors = mainBottomBarColor
        )
        NavigationBarItem(
            icon = { Icon(painter =  painterResource(id =R.drawable.pokeball) , contentDescription = "prueba") },

            selected = index == 1,
            onClick = {index = 1},
            colors = mainBottomBarColor
        )

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "prueba") },
            label = { Text("Buscar") },
            selected = index == 2,
            onClick = {index = 2},
            colors = mainBottomBarColor
        )
    }
}