package com.example.composetest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AuthorizationScreenViewModel : ViewModel() {
    var modelFlow by mutableStateOf(AuthorizationScreenModel())

    /*Надо спросить почему именно через copy, почему нельзя просто
            modelFlow.value.loginField = newLoginFieldText*/

    fun onLoginFieldChanged(newLoginFieldText: String) {
        modelFlow = modelFlow.copy(loginField = newLoginFieldText)
    }

    fun onPasswordFieldChanged(newPasswordFieldText: String) {
        modelFlow = modelFlow.copy(passwordField = newPasswordFieldText)
    }
}