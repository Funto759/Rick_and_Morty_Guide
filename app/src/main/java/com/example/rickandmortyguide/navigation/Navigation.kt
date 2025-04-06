package com.example.rickandmortyguide.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.rickandmortyguide.ui.BookmarkScreen
import com.example.rickandmortyguide.ui.CharacterCardDetails
import com.example.rickandmortyguide.ui.RickAndMortyCharactersListScreen
import com.example.rickandmortyguide.ui.SettingsScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController, startDestination = "CharactersScreen") {  // Use the passed-in one
        composable("CharactersScreen") {
            RickAndMortyCharactersListScreen(navController)
        }
        composable("BookmarkScreen") {
            BookmarkScreen(navController, Modifier)
        }
        composable("SettingsScreen") {
            SettingsScreen(navController, Modifier)
        }
        composable<CharactersDetailScreen> {
            val args = it.toRoute<CharactersDetailScreen>()
            CharacterCardDetails(navController, args.id)
        }
    }

}

@Serializable
object BookmarkScreen

@Serializable
object SettingsScreen

@Serializable
object CharactersScreen

@Serializable
data class CharactersDetailScreen(val id:Int)