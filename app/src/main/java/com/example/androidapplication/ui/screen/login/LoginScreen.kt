package com.example.androidapplication.ui.screen.login

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidapplication.models.login.LoginState
import com.example.androidapplication.models.login.LoginViewModel
import com.example.androidapplication.ui.theme.PrimaryYellowDark
import com.example.androidapplication.ui.theme.PrimaryYellowLight

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit = {},
    onRegistrationClicked: () -> Unit = {},
    onForgotPasswordClicked: () -> Unit = {}
) {
    val viewModel = remember { LoginViewModel() }
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
// Handle side-effects safely
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                Toast.makeText(context, (loginState as LoginState.Success).message, Toast.LENGTH_SHORT).show()
                onLoginClicked()
            }
            is LoginState.Error -> {
                Toast.makeText(context, (loginState as LoginState.Error).error, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
    Box(
        modifier = modifier
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
            // Title animation (kept)
            val titleOpacity by animateFloatAsState(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1500)
            )

            // Title Section (Lottie removed, spacing adjusted)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Welcome Back!",
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Text(
                    text = "Login.",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            Column(modifier = Modifier.fillMaxWidth()) {

                // Email Field
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            if (email.isEmpty()) {
                                Text(text = "Your email", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Password Field
                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.White),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            if (password.isEmpty()) {
                                Text(text = "Password", color = Color.Gray)
                            }
                            innerTextField()
                        }
                    }
                )
                var rememberMe by remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = PrimaryYellowDark,
                            uncheckedColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Remember Me", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Forgot Password
                TextButton(
                    onClick = onForgotPasswordClicked,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Forgot Password?", color = Color.Black)
                }


                // Login Button
                Button(
                    onClick = { viewModel.loginUser(email, password, context, rememberMe) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryYellowDark,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login")
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            // Register Button
            Button(
                onClick = onRegistrationClicked,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryYellowDark,
                    contentColor = Color.White
                )
            ) {
                Text("Register")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
