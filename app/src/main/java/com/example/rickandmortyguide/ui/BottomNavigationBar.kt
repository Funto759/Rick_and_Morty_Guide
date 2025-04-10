package com.example.rickandmortyguide.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rickandmortyguide.data.BottomNavItem

@Composable
fun BottomNavigationBar(
    items : List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(modifier = modifier, backgroundColor = androidx.compose.ui.graphics.Color.DarkGray, elevation = 5.dp) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.White, // This will be applied to the Icon
                unselectedContentColor = Color.Gray, // This will be applied to the Icon
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.image,
                            contentDescription = "NavIcon"
                        )
                        if (selected) {
                            Text(
                                item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = Color.Green // Apply color to selected text here
                            )
                        } else {
                            Text(
                                item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = Color.Gray // Apply color to unselected text here
                            )
                        }
                    }
                }
            )

        }
    }
}