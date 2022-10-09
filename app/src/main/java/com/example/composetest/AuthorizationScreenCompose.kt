package com.example.composetest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AppCompose.AuthorizationScreen(
    model: AuthorizationScreenModel,
    onIntent: (Intent) -> Unit
) {
    Column {
        AuthorizationTextField(
            label = "Login",
            value = model.loginField,
            onValueChange = { newLoginFieldText ->
                onIntent(Intent.LoginFieldChanged(newLoginFieldText))
            }
        )

        AuthorizationTextField(
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            value = model.passwordField,
            onValueChange = { newPasswordFieldText ->
                onIntent(Intent.PasswordFieldChanged(newPasswordFieldText))
            }
        )
    }
}

@Composable
private fun AuthorizationTextField(
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}