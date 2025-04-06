package com.example.rickandmortyguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyguide.data.BottomNavItem
import com.example.rickandmortyguide.navigation.Navigation
import com.example.rickandmortyguide.ui.BottomNavigationBar
import com.example.rickandmortyguide.ui.theme.RickAndMortyGuideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyGuideTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomNavigationBar(modifier = Modifier.navigationBarsPadding(),items =
                        listOf(
                            BottomNavItem(
                                name = "Home",
                                route ="CharactersScreen" ,
                                image = Icons.Default.Home,
                                badgeCount = 0
                            ),
                            BottomNavItem(
                                name = "Bookmarks",
                                route ="BookmarkScreen" ,
                                image = Icons.Default.Bookmark,
                                badgeCount = 0
                            ),
                            BottomNavItem(
                                name = "Settings",
                                route ="SettingsScreen" ,
                                image = Icons.Default.Settings,
                                badgeCount = 0
                            )
                        )
                        , navController = navController, onItemClick = { navController.navigate(it.route)})
                }) { innerPadding ->
                    Surface (modifier = Modifier.padding(innerPadding).navigationBarsPadding()){
                        Navigation(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyGuideTheme {
        Greeting("Android")
    }
}