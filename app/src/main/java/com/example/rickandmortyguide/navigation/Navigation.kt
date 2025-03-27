package com.example.rickandmortyguide.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.rickandmortyguide.ui.CharacterCardDetails
import com.example.rickandmortyguide.ui.RickAndMortyCharactersListScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CharactersScreen){
        composable<CharactersScreen>{
            RickAndMortyCharactersListScreen(navController)
        }
        composable<CharactersDetailScreen>{
            val args = it.toRoute<CharactersDetailScreen>()
            CharacterCardDetails(navController,args.id)
        }
    }

}

@Serializable
object CharactersScreen

@Serializable
data class CharactersDetailScreen(val id:Int)