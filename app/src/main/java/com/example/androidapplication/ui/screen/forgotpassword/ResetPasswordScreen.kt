package com.example.androidapplication.ui.screen.resetpassword

import ResetPasswordViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.androidapplication.ui.theme.PrimaryYellowDark
import com.example.androidapplication.ui.theme.PrimaryYellowLight
import com.example.androidapplication.R
import androidx.compose.ui.focus.onFocusChanged

import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
@Composable
fun ResetPasswordScreen(
    navHost: NavController,
    resetToken: String
) {
    val context = LocalContext.current
    val viewModel: ResetPasswordViewModel = viewModel()

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Track focus for animations
    var isFocused by remember { mutableStateOf(false) }

    // Load Lottie animations
    val lottieComp1 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.visibility))
    val lottieComp2 by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.visibility))
    val lottieAnim1 = rememberLottieAnimatable()
    val lottieAnim2 = rememberLottieAnimatable()

    LaunchedEffect(lottieComp1) { lottieAnim1.animate(lottieComp1, iterations = LottieConstants.IterateForever) }
    LaunchedEffect(lottieComp2) { lottieAnim2.animate(lottieComp2, iterations = LottieConstants.IterateForever) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to PrimaryYellowDark,
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
            Text(
                text = "Reset Password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your new password",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Show animations when focused
            if (isFocused) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LottieAnimation(
                        composition = lottieComp1,
                        progress = { lottieAnim1.progress },
                        modifier = Modifier.size(80.dp)
                    )
                    LottieAnimation(
                        composition = lottieComp2,
                        progress = { lottieAnim2.progress },
                        modifier = Modifier.size(80.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // New Password TextField
            BasicTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(18.dp))
                    .padding(16.dp)
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (newPassword.isEmpty()) Text("New password", color = Color.Gray)
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password TextField
            BasicTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(18.dp))
                    .padding(16.dp)
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (confirmPassword.isEmpty()) Text("Confirm password", color = Color.Gray)
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Reset Button
            Button(
                onClick = {
                    if (newPassword != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    viewModel.resetPassword(
                        newPassword = newPassword,
                        resetToken = resetToken,
                        onSuccess = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            navHost.navigate("login") { popUpTo(0) }
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryYellowDark,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text("Reset Password")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = { navHost.navigate("login") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to Login", color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResetPasswordScreenPreview() {
    val fakeNavController = rememberNavController()
    ResetPasswordScreen(
        navHost = fakeNavController,
        resetToken = "dummy_token"
    )
}
