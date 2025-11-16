package com.example.androidapplication.ui.artiste

import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

// No explicit KeyboardOptions or KeyboardActions import, but still using its members
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType

import coil.compose.AsyncImage

import com.example.androidapplication.models.artiste.ArtistViewModel
import com.example.androidapplication.models.artiste.Artist

import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistListScreen(navController: NavController, artistViewModel: ArtistViewModel = viewModel()) {
    val artists by artistViewModel.artists
    val isLoading by artistViewModel.isLoading
    val error by artistViewModel.error
    var searchText by remember { mutableStateOf("") }
    val famousThemes = listOf("Impressionism", "Renaissance", "Surrealism", "Abstract", "Pop Art")

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AI Art Curator") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search by Theme") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // Using KeyboardOptions directly as a parameter without explicit import of the type
                // 1. Définissez l'APPARENCE du bouton du clavier ici
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search // Utilisez .Search pour l'action de recherche
                ),

                // 2. Définissez l'ACTION à exécuter lorsque ce bouton est pressé
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Le code que vous voulez exécuter
                        artistViewModel.fetchArtists(searchText)
                        keyboardController?.hide() // Cache le clavier après la recherche
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Famous Theme Buttons
            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(famousThemes) { theme ->
                    Button(
                        onClick = {
                            searchText = theme
                            artistViewModel.fetchArtists(theme)
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = theme,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(error!!, color = MaterialTheme.colorScheme.error, fontSize = 16.sp)
                }
            } else {
                LazyColumn {
                    items(artists) { artist ->
                        ArtistListItem(artist = artist) {
                            val encodedStyleDescription =
                                URLEncoder.encode(artist.style_description, "UTF-8")
                            val encodedFamousWorks =
                                URLEncoder.encode(artist.famous_works.joinToString(","), "UTF-8")
                            navController.navigate("artistDetail/${artist.name}/${encodedStyleDescription}/${artist.country}/${encodedFamousWorks}")
                        }
                    }
                }
            }
        }
    }

}
@Composable
fun ArtistListItem(artist: Artist, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = LocalIndication.current,
                onClick = onClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://picsum.photos/seed/${artist.name.hashCode()}/100/100",
                contentDescription = "${artist.name} statistic image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .weight(0.3f),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(0.7f)) {
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = artist.country,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}