package com.example.pok3search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pok3search.ui.theme.iconUnselected
import com.example.pok3search.ui.theme.pokeRed
import com.example.pok3search.ui.theme.pokeRedSelected


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navigationController: NavHostController) {

    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            MainBottomBar(navigationController, selectedIndex) { newIndex ->
                selectedIndex = newIndex
            }
        }
    ) {

        NavHost(navController = navigationController, startDestination = "MainScreen") {
            composable("MainScreen") {
                selectedIndex = 0
                MainScreen()
            }
            composable("Pokemondetail") {
                selectedIndex = 1
                Pokemondetail()
            }
            composable("SearchScreen") {
                selectedIndex = 2
                SearchScreen()
            }
        }
    }
}

@Composable
fun MainBottomBar(
    navigationController: NavHostController,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val mainBottomBarColor = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = iconUnselected,
        selectedTextColor = Color.White,
        indicatorColor = pokeRedSelected,
        unselectedTextColor = iconUnselected
    )

    NavigationBar(containerColor = Color.Red) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Pokemones") },
            label = { Text("Pokemones") },
            selected = selectedIndex == 0,
            onClick = {
                if (selectedIndex != 0) {
                    onItemSelected(0)
                    navigationController.navigate("MainScreen")
                }
            },
            colors = mainBottomBarColor
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.pokeball),
                    contentDescription = "Pokemondetail"
                )
            },
            selected = selectedIndex == 1,
            onClick = {
                if (selectedIndex != 1) {
                    onItemSelected(1)
                    navigationController.navigate("Pokemondetail")
                }
            },
            colors = mainBottomBarColor
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            selected = selectedIndex == 2,
            onClick = {
                if (selectedIndex != 2) {
                    onItemSelected(2)
                    navigationController.navigate("SearchScreen")
                }
            },
            colors = mainBottomBarColor
        )
    }
}
