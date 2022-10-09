package com.example.composetest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AuthorizationScreenViewModel : ViewModel() {
    val modelFlow = MutableStateFlow(AuthorizationScreenModel())

    fun onIntent(intent: Intent) {
        when(intent) {
            is Intent.LoginFieldChanged -> loginFieldChanged(intent.loginField)
            is Intent.PasswordFieldChanged -> passwordFieldChanged(intent.passwordField)
        }
    }

    private fun loginFieldChanged(newLoginFieldText: String) {
        modelFlow.value = modelFlow.value.copy(loginField = newLoginFieldText)
    }

    private fun passwordFieldChanged(newPasswordFieldText: String) {
        modelFlow.value = modelFlow.value.copy(passwordField = newPasswordFieldText)
    }
}

sealed interface Intent {
    class LoginFieldChanged(val loginField: String) : Intent
    class PasswordFieldChanged(val passwordField: String) : Intent
}

data class AuthorizationScreenModel(
    val loginField: String = "",
    val passwordField: String = ""
)