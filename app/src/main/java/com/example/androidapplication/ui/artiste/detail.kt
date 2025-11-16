package com.example.androidapplication.ui.artiste

// IMPORTANT: Remove all conflicting Material 1 imports (like androidx.compose.material.*)
// The following imports are for Material 3 components and icons
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api // For TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.CardDefaults // Needed for Card elevation and colors

// Material Icons Extended for the back arrow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // This is fine, it's a standard icon

import androidx.compose.animation.*
import androidx.compose.foundation.background // If this is for a Modifier, it's fine
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
// REMOVED: import androidx.compose.material.* // <-- THIS IS THE PRIMARY CONFLICTING IMPORT
// REMOVED: import com.example.androidapplication.ui.theme.* // Make sure this theme uses Material3 internally

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // General Color is fine
import androidx.compose.ui.graphics.vector.ImageVector // General ImageVector is fine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage // <-- Add this import for Coil's AsyncImage

import java.net.URLDecoder // Add for decoding arguments

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    navController: NavController,
    artistName: String,
    styleDescription: String,
    country: String,
    famousWorks: String // Passed as a single string, will be split
) {
    // Decode URL encoded arguments
    val decodedStyleDescription = URLDecoder.decode(styleDescription, "UTF-8")
    val decodedFamousWorks = URLDecoder.decode(famousWorks, "UTF-8")
    val worksList = decodedFamousWorks.split(",").filter { it.isNotBlank() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(artistName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack, // Access via Icons.Filled
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                // Material 3 Card requires CardDefaults.cardElevation() for elevation
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(12.dp),
                // Material 3 Card uses colors = CardDefaults.cardColors(containerColor = ...)
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AsyncImage( // <-- This should now resolve
                        model = "https://picsum.photos/seed/${artistName.hashCode() + 1}/400/300",
                        contentDescription = "$artistName's artistic representation",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )

                    Text(
                        text = artistName,
                        // Material 3 Typography - replaced h4
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary // Material 3 colorScheme
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Country: $country",
                        // Material 3 Typography - replaced subtitle1
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Art Style Description:",
                        // Material 3 Typography - replaced h6
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = decodedStyleDescription,
                        // Material 3 Typography - replaced body1
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 24.sp
                    )
                }
            }

            if (worksList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Famous Works:",
                    // Material 3 Typography - replaced h6
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(worksList) { work ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
                        ) {
                            Text(
                                text = "• $work",
                                // Material 3 Typography - replaced body1
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "No famous works listed for this artist.",
                    // Material 3 Typography - replaced body1
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}