package com.example.rickandmortyguide.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController,modifier: Modifier){
    Box(Modifier.fillMaxSize(),
        Alignment.Center){
        Text("Settings",color = Color.White)
    }
}

@Preview
@Composable
fun SettingsScreenPreview(){
    SettingsScreen(navController = NavController(LocalContext.current),Modifier)
}