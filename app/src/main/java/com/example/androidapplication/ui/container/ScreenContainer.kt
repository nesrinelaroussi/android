package com.example.androidapplication.ui.container

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidapplication.models.login.getSavedTokens
import com.example.androidapplication.ui.artiste.ArtistDetailScreen
import com.example.androidapplication.ui.artiste.ArtistListScreen
import com.example.androidapplication.ui.screen.forgotpassword.ForgotPasswordScreen
import com.example.androidapplication.ui.screen.forgotpassword.VerifyOtpScreen
import com.example.androidapplication.ui.screen.home.HomeScreen
import com.example.androidapplication.ui.screen.login.LoginScreen
import com.example.androidapplication.ui.screen.welcome.WelcomeScreen
import com.example.androidapplication.ui.screen.profile.ProfileScreen  // Import ProfileScreen
import com.example.androidapplication.ui.screen.profile.EditProfileScreen  // Import ProfileScreen
import com.example.androidapplication.ui.screen.resetpassword.ResetPasswordScreen
import com.example.androidapplication.models.artiste.ArtistViewModel
import java.net.URLDecoder


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContainer() {
    val navHost = rememberNavController()
    val context = LocalContext.current
    val artistViewModel: ArtistViewModel = viewModel()

    val (accessToken, refreshToken) = getSavedTokens(context)
    val startDestination = if (!refreshToken.isNullOrEmpty()) {
        NavGraph.ArtistList.route
    } else {
        NavGraph.Welcome.route
    }

    NavHost(
        navController = navHost,
        startDestination = startDestination
    ){
        composable(NavGraph.Welcome.route) {
            WelcomeScreen(
                onOpenLoginClicked = {
                    navHost.navigate(NavGraph.Login.route)
                },
                onAutoLogin = {
                    navHost.navigate(NavGraph.Home.route) {
                        popUpTo(NavGraph.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavGraph.Login.route) {
            LoginScreen(
                onLoginClicked = {
                    navHost.navigate(NavGraph.Home.route) {
                        popUpTo(NavGraph.Login.route) { inclusive = true }
                    }
                },
                onRegistrationClicked = {
                    navHost.navigate(NavGraph.Registration.route)
                },
                onForgotPasswordClicked = { navHost.navigate(NavGraph.ForgotPassword.route) }
            )
        }
        composable(NavGraph.Home.route) {
            HomeScreen(navController = navHost)   // ✅ FIXED
        }

        composable(NavGraph.Profile.route) {
            ProfileScreen(onEditClicked = {
                navHost.navigate(NavGraph.Edit.route)
            })
        }
        composable(NavGraph.Edit.route) {
            EditProfileScreen(onprofileClicked = {
                navHost.navigate(NavGraph.Profile.route)
            })
        }
        composable(NavGraph.ForgotPassword.route) {
            ForgotPasswordScreen(
                onRestPasswordClicked = { email ->
                    navHost.navigate(NavGraph.VerifyOtp.route + "?email=$email")
                },
                onBackToLoginClicked = {
                    navHost.navigate(NavGraph.Login.route) // 👈 navigate back to Login screen
                }
            )
        }


        composable(NavGraph.VerifyOtp.route) { backStackEntry ->
            VerifyOtpScreen(navHost = navHost)
        }

        composable(
            route = NavGraph.ResetPassword.route + "?resetToken={resetToken}",
            arguments = listOf(navArgument("resetToken") { defaultValue = "" })
        ) { backStackEntry ->
            val resetToken = backStackEntry.arguments?.getString("resetToken") ?: ""
            ResetPasswordScreen(navHost = navHost, resetToken = resetToken)
        }

        composable(NavGraph.ArtistList.route) {
            // Pass the ViewModel instance to the ArtistListScreen
            ArtistListScreen(navController = navHost, artistViewModel = artistViewModel)
        }

        composable(
            route = "artistDetail/{artistName}/{styleDescription}/{country}/{famousWorks}",
            arguments = listOf(
                navArgument("artistName") { type = NavType.StringType },
                navArgument("styleDescription") { type = NavType.StringType },
                navArgument("country") { type = NavType.StringType },
                navArgument("famousWorks") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Retrieve arguments from the backStackEntry
            val artistName = URLDecoder.decode(backStackEntry.arguments?.getString("artistName") ?: "", "UTF-8")
            val styleDescription = URLDecoder.decode(backStackEntry.arguments?.getString("styleDescription") ?: "", "UTF-8")
            val country = URLDecoder.decode(backStackEntry.arguments?.getString("country") ?: "", "UTF-8")
            val famousWorks = URLDecoder.decode(backStackEntry.arguments?.getString("famousWorks") ?: "", "UTF-8")


            ArtistDetailScreen(
                navController = navHost,
                artistName = artistName,
                styleDescription = styleDescription,
                country = country,
                famousWorks = famousWorks
            )
        }
    }

}



