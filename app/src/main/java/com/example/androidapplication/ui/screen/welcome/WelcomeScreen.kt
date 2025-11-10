package com.example.androidapplication.ui.screen.welcome

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.androidapplication.R
import com.example.androidapplication.ui.components.ActionButton
import com.example.androidapplication.ui.theme.*

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onOpenLoginClicked: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.pencil))

    // State to control Lottie's horizontal position
    var animationOffset by remember { mutableStateOf(-300.dp) }
    val animatedOffset by androidx.compose.animation.core.animateDpAsState(
        targetValue = animationOffset,
        animationSpec = tween(durationMillis = 7500) // Adjust duration as needed
    )

    // States for controlling visibility
    var isTitleVisible by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(false) }

    // Trigger animations
    LaunchedEffect(Unit) {
        isTitleVisible = true // Make the title visible with animation
        animationOffset = 0.dp // Move the Lottie animation to the center
        isButtonVisible = true // Show the button when the Lottie starts
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryYellowDark ,
                    0.6f to PrimaryYellowLight,
                    1f to PrimaryYellowLight,
                )
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(54.dp) // Add space before the title
        )
        // Animated visibility for the title
        AnimatedVisibility(
            visible = isTitleVisible,
            enter = fadeIn(animationSpec = tween(2000)), // Fade in over 2 seconds
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Text(
                text = "Create art with ease!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(horizontal = 21.dp),
                color = DarkTextColor
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Spacer(
            modifier = Modifier.weight(weight = 1f)
        )

        // LottieAnimation with offset animation
        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE, // Loop animation indefinitely
            modifier = Modifier
                .size(300.dp) // Set Lottie size
                .offset(x = animatedOffset) // Animate horizontal movement
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Animated visibility for the button
        AnimatedVisibility(
            visible = isButtonVisible,
            enter = slideInVertically(
                animationSpec = tween(2500),
                initialOffsetY = { it } // Start from below the screen
            )
        ) {
            ActionButton(
                text = "Next",
                isNavigationArrowVisible = true,
                onClicked = onOpenLoginClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryYellowDark,
                    contentColor = PrimaryYellowLight
                ),
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onOpenLoginClicked = {})
}

