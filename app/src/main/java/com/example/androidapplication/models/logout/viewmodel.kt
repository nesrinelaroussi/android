package com.example.androidapplication.models.logout


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidapplication.remote.RetrofitClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LogoutViewModel : ViewModel() {

    private val _logoutState = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val logoutState: StateFlow<LogoutState> get() = _logoutState

    fun logout(refreshToken: String, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.logout(LogoutRequest(refreshToken))

                if (response.isSuccessful) {
                    clearTokens(context)
                    _logoutState.value = LogoutState.Success("Logged out successfully")
                } else {
                    _logoutState.value = LogoutState.Error("Logout failed")
                }

            } catch (e: Exception) {
                _logoutState.value = LogoutState.Error("Error: ${e.message}")
            }
        }
    }
}
fun clearTokens(context: Context) {
    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    prefs.edit().clear().apply()
}

sealed class LogoutState {
    object Idle : LogoutState()
    data class Success(val message: String) : LogoutState()
    data class Error(val error: String) : LogoutState()
}
