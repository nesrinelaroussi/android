package com.example.androidapplication.ui.screen.forgotpassword

import VerifyOtpViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidapplication.ui.container.NavGraph
import com.example.androidapplication.ui.theme.PrimaryYellowDark
import com.example.androidapplication.ui.theme.PrimaryYellowLight
@OptIn(ExperimentalMaterial3Api::class) // ✅ explicitly opt-in instead of warning
@Composable
fun VerifyOtpScreen(
    navHost: NavController
) {
    val context = LocalContext.current
    val viewModel: VerifyOtpViewModel = viewModel()

    var otp by remember { mutableStateOf(List(6) { "" }) }

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
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verify OTP",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter the 6-digit code",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // OTP TextFields
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                otp.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            if (it.length <= 1) {
                                otp = otp.toMutableList().also { list -> list[index] = it }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                            .background(Color.White, shape = RoundedCornerShape(12.dp)),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedTextColor = Color.Black, // ✅ replaced 'textColor'
                        focusedBorderColor = PrimaryYellowDark,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = PrimaryYellowDark
                    )

                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Verify Button
            Button(
                onClick = {
                    val enteredOtp = otp.joinToString("") // join 6 digits
                    viewModel.verifyOtp(
                        otp = enteredOtp,
                        onSuccess = { resetToken ->
                            Toast.makeText(context, "OTP verified!", Toast.LENGTH_SHORT).show()
                            navHost.navigate("${NavGraph.ResetPassword.route}?resetToken=$resetToken")
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
                Text("Verify OTP")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyOtpScreenPreview() {
    val fakeNavController = rememberNavController()
    VerifyOtpScreen(navHost = fakeNavController)
}
