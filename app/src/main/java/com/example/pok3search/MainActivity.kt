package com.example.pok3search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pok3search.pokedex.domain.model.Pokemon
import com.example.pok3search.pokedex.ui.detailpokemon.DetailPokemonViewModel
import com.example.pok3search.pokedex.ui.listpokemons.ListPokemonViewModel
import com.example.pok3search.ui.theme.Pok3SearchTheme
import com.example.pok3search.ui.theme.mainBackgroundColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val detailPokemonViewModel: DetailPokemonViewModel by viewModels()
    private val listPokemonViewModel: ListPokemonViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { viewModel.isLoading.value }

        setContent {
            Pok3SearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(listPokemonViewModel, detailPokemonViewModel)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(listPokemonViewModel: ListPokemonViewModel, detailPokemonViewModel: DetailPokemonViewModel) {

    val navigationController = rememberNavController()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(mainBackgroundColor)){

        NavHost(navController = navigationController, startDestination = "MainScreen") {
            composable("MainScreen") {
                MainScreen(listPokemonViewModel,navigationController)
            }
            composable(
                "Pokemondetail/{pokemonId}/{pokemonName}",
                arguments = listOf(navArgument("pokemonId") {
                    type = NavType.IntType
                }, navArgument("pokemonName") {
                    type = NavType.StringType
                })
            ) {
                val pokemon = Pokemon(id= it.arguments?.getInt("pokemonId") ?: 0, name = it.arguments?.getString("pokemonName") ?: "")
                PokemonDetail(pokemon,navigationController,detailPokemonViewModel )
            }

        }
    }

}



