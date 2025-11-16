package com.example.androidapplication.models.artiste

// ArtistViewModel.kt

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapplication.remote.RetrofitClient
import kotlinx.coroutines.launch

class ArtistViewModel : ViewModel() {
    val artists = mutableStateOf<List<Artist>>(emptyList())
    val isLoading = mutableStateOf(false)
    val error = mutableStateOf<String?>(null)
    val currentTheme = mutableStateOf("Modern Art") // Default theme

    init {
        fetchArtists(currentTheme.value)
    }

    fun fetchArtists(theme: String) {
        currentTheme.value = theme
        isLoading.value = true
        error.value = null
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getArtists(theme)
                artists.value = response.artists
            } catch (e: Exception) {
                error.value = "Failed to load artists: ${e.message}"
                println("Error fetching artists: ${e.message}") // For debugging
            } finally {
                isLoading.value = false
            }
        }
    }
}