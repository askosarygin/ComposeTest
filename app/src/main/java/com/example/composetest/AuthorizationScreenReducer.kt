package com.example.composetest

class AuthorizationScreenReducer(
) {

    fun reduceAuthorizationScreenModel(model: AuthorizationScreenModel, intent: Intent) =
        model.reduce(intent)

    private fun AuthorizationScreenModel.reduce(intent: Intent) = when (intent) {
        is Intent.LoginFieldChanged -> loginFieldChanged(intent)
        is Intent.PasswordFieldChanged -> passwordFieldChanged(intent)
        is Intent.SavedSymbolsChanged -> savedSymbolsChanged(intent)
    }

    private fun AuthorizationScreenModel.loginFieldChanged(intent: Intent.LoginFieldChanged) =
        copy(
            loginField = intent.loginField
        )

    private fun AuthorizationScreenModel.passwordFieldChanged(intent: Intent.PasswordFieldChanged) =
        copy(
            passwordField = intent.passwordField
        )

    private fun AuthorizationScreenModel.savedSymbolsChanged(
        intent: Intent.SavedSymbolsChanged
    ): AuthorizationScreenModel {
        val savedSymbols = savedSymbols
        savedSymbols.add(intent.savedSymbol)
        return copy(
            savedSymbols = savedSymbols
        )
    }
}