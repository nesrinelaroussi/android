package com.example.androidapplication.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidapplication.models.UserDataResponse
import com.example.androidapplication.models.ProfileViewModel
import com.example.androidapplication.ui.theme.PrimaryGreenLight
import com.example.androidapplication.ui.theme.PrimaryYellowLight
import com.example.androidapplication.utils.getTokenFromPreferences

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel(), onEditClicked: () -> Unit = {}) {
    // Fetch the token securely (replace this with your actual token retrieval logic)
    val token = getTokenFromPreferences(context = LocalContext.current)  // Use LocalContext.current to get the current context

   /* // Log token for debugging purposes
    Log.d("ProfileScreen", "JWT Token: $token")

    // Fetch user data by passing the token only if it's valid
    if (!token.isNullOrEmpty()) {
        profileViewModel.fetchUserData()  // Fetch user data without needing to pass the token explicitly
    } else {
        Log.e("ProfileScreen", "JWT Token is null or empty!")
    }

    // Observe LiveData for user data

    // Observe LiveData for error messages
    val errorMessage = profileViewModel.error.observeAsState(initial = "").value

    // Observe LiveData for logout message
    val logoutMessage = profileViewModel.logoutMessage.observeAsState(initial = "").value

    // Observe loading state (you could add a loading state in your ViewModel if necessary)
    val isLoading = userData.id.isEmpty() && errorMessage.isEmpty()  // Simple check for loading state

    // Layout for displaying user data or error message
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryGreenLight,
                    0.5f to PrimaryYellowLight,
                    1f to Color.White
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                // Show a loading spinner while the data is being fetched
                CircularProgressIndicator()
            } else if (errorMessage.isNotEmpty()) {
                // Show error message if something goes wrong
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            } else {
                // Display user data in a Card
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("User ID: ${userData.id}", fontSize = 18.sp)
                        Text("User Name: ${userData.name}", fontSize = 18.sp)
                        Text("User Email: ${userData.email}", fontSize = 18.sp)
                    }
                }
            }

            // Show logout message
            logoutMessage?.let {
                Text(text = it, color = Color.Red, fontSize = 16.sp)
            }

            // Logout Button
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Call logout function from ViewModel

                    // profileViewModel.logout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

            // Edit Button (You can handle navigation here)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onEditClicked,  // Trigger the edit profile screen
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Profile")
            }
        }
    }*/
}
