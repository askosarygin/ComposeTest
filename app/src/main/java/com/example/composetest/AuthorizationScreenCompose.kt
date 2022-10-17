package com.example.composetest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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
        Button(onClick = {
            onIntent(Intent.SavedSymbolsChanged(model.loginField))
            onIntent(Intent.LoginFieldChanged(""))
        }) {
            Text(text = "Submit")
        }
        ListOfSymbols(list = model.savedSymbols)
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

@Composable
private fun ListOfComposableItem(symbols: String) {
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth(0.7f)) {
        Row {
            Text(text = "Last text from login:", modifier = Modifier.weight(1f))
            Text(text = symbols)
        }
    }
}

@Composable
private fun ListOfSymbols(list: List<String>) {
    LazyColumn {
        items(items = list) { symbols ->
            ListOfComposableItem(symbols = symbols)
        }
    }
}