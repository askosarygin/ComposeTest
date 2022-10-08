package com.example.composetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.composetest.databinding.FragmentOneBinding
import kotlinx.coroutines.flow.asStateFlow

class AuthorizationScreenFragment : Fragment() {
    private val viewModel by viewModels<AuthorizationScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOneBinding
        .inflate(layoutInflater, container, false)
        .apply {
            composeView.setContent {
                AuthorizationScreen(
                    viewModel = viewModel
                )
            }
        }.root

    @Composable
    fun AuthorizationScreen(
        viewModel: AuthorizationScreenViewModel
    ) {
        Column {
            AuthorizationTextField(
                label = "Login",
                value = viewModel.modelFlow.loginField,
                onValueChange = { newLoginFieldText ->
                    viewModel.onLoginFieldChanged(newLoginFieldText)
                }
            )

            AuthorizationTextField(
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                value = viewModel.modelFlow.passwordField,
                onValueChange = { newPasswordFieldText ->
                    viewModel.onPasswordFieldChanged(newPasswordFieldText)
                }
            )
        }
    }

    @Composable
    fun AuthorizationTextField(
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
}