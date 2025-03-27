package com.example.rickandmortyguide.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.data.Result
import com.example.rickandmortyguide.model.RickAndMortyViewModel
import com.example.rickandmortyguide.navigation.CharactersDetailScreen

@Composable
fun RickAndMortyCharactersListScreen(
        navController: NavController,
    ) {
    val viewModel: RickAndMortyViewModel = hiltViewModel()
//        val state by viewModel.articlesDetails.collectAsStateWithLifecycle()

    val characters = viewModel.getCharacters().collectAsLazyPagingItems()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "NYC Logo",
                Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(5.dp))
            InstructionTextDetails("Rick And Morty")
            when (characters.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoadState.Error -> {
                    Text("error")

                }

                else -> {
                    Recycler(characters,navController)
                }
            }
            when (characters.loadState.append) {
                is LoadState.Loading ->  CircularProgressIndicator()
                is LoadState.Error -> Text(text = "Error loading more characters")
                else -> {}
            }
        }


    }

}


        @Composable
        fun searchBar(
            modifier: Modifier = Modifier,
            hint: String = "",
            onSearch: (String) -> Unit = {}
        ) {

            var hintText by remember { mutableStateOf("") }

            var isHintDisplayed by remember { mutableStateOf(hint != "") }

            Box(Modifier) {
                BasicTextField(
                    value = hintText,
                    onValueChange = {
                        hintText = it
                        onSearch(hintText)
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(Color.Black),
                    modifier = Modifier.fillMaxWidth().shadow(5.dp, CircleShape).background(
                        Color.White,
                        CircleShape
                    ).padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }

        }


    @Composable
    fun InstructionTextDetails(display: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .shadow(4.dp, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD) // Light background for contrast
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "📚 $display",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF1E88E5), // Soft blue for the text
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    }

@Composable
fun Recycler(characters: LazyPagingItems<Result>,navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count = characters.itemCount) {
            val char = characters[it]
            if (char != null) {
                CharacterCard(char,navController)
            }
        }
    }
}

@Composable
fun CharacterCard(it: Result,navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate(CharactersDetailScreen(it.id)) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Book Rank
            Text(
                text = "Status #${it.status}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Book Image
            Image(
                painter = rememberAsyncImagePainter(it.image),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title and Author
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "by ${it.species}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = it.gender,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Price Section
            Text(
                text = "${it.location.name}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF03DAC5)
            )
        }
    }
}


    @Preview(showBackground = true)
    @Composable
    private fun preview(name: String = "Manga"){
//        NYCTimesTheme {
//            InstructionTextDetails(name)
//        }
    }

