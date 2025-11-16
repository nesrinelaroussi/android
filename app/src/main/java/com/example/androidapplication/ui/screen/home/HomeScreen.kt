
package com.example.androidapplication.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidapplication.models.login.getRefreshToken
import com.example.androidapplication.models.logout.LogoutState
import com.example.androidapplication.models.logout.LogoutViewModel
import com.example.androidapplication.models.logout.clearTokens
import com.example.androidapplication.remote.RetrofitClient
import com.example.androidapplication.ui.container.NavGraph
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember { LogoutViewModel() }
    val logoutState by viewModel.logoutState.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF7F8FB),

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Already here */ },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_view), "") },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_myplaces), "") },
                    label = { Text("Profile") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(NavGraph.ArtistList.route) },
                    icon = { Icon(painterResource(id = android.R.drawable.ic_menu_manage), "") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("HI☻")

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                val refreshToken = getRefreshToken(context)
                if (refreshToken != null) {
                    viewModel.logout(refreshToken, context)
                }
            }) {
                Text("Logout")
            }

            LaunchedEffect(logoutState) {
                if (logoutState is LogoutState.Success) {
                    navController.navigate(NavGraph.Login.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }
}





