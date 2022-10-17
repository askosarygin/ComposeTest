package com.example.composetest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthorizationScreenViewModel : ViewModel() {
    private val mutex = Mutex()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val modelFlow = MutableStateFlow(AuthorizationScreenModel())

    private val reducer = AuthorizationScreenReducer()

    fun onIntent(intent: Intent) {
        runBlocking {//ток я все равно не понял зачем тут делать блокировку основного потока
            mutex.withLock {
                val oldModel = modelFlow.value
                val newModel = reducer.reduceAuthorizationScreenModel(oldModel, intent)
                if (oldModel != newModel) {
                    modelFlow.value = newModel
                }
            }
        }
    }
}

sealed interface Intent {
    class LoginFieldChanged(val loginField: String) : Intent
    class PasswordFieldChanged(val passwordField: String) : Intent
    class SavedSymbolsChanged(val savedSymbol: String) : Intent
}

data class AuthorizationScreenModel(
    val loginField: String = "",
    val passwordField: String = "",
    val savedSymbols: MutableList<String> = mutableListOf()
)