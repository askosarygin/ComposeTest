package com.example.authorizationtest

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthorizationScreenViewModel : ViewModel() {
    private val reducer = AuthorizationScreenReducer()
    private val mutex = Mutex()

    val modelFlow = MutableStateFlow(AuthorizationScreenModel())

    fun onIntent(intent: Intent) {
        runBlocking {//блокируем поток который вызвал onIntent и заходим в код ниже по ключу
            // чтобы не было двух записей в model одновременно
            mutex.withLock {
                val oldModel = modelFlow.value
                val newModel = reducer.reduceAuthorizationScreenModel(oldModel, intent)
                if (oldModel != newModel) {
                    modelFlow.value = newModel
                }
            }
        }
    }

    fun clearAllFields() {
        onIntent(Intent.SurnameFieldChanged(""))
        onIntent(Intent.NameFieldChanged(""))
        onIntent(Intent.PatronymicFieldChanged(""))
        onIntent(Intent.SexFieldChanged(""))
        onIntent(Intent.SubscribedCheckboxChanged(false))
    }
}

sealed interface Intent {
    class NameFieldChanged(val newName: String) : Intent
    class SurnameFieldChanged(val newSurname: String) : Intent
    class PatronymicFieldChanged(val newPatronymic: String) : Intent
    class SexFieldChanged(val newSex: String) : Intent
    class SubscribedCheckboxChanged(val newCheckboxSelection: Boolean) : Intent
    class AuthorizedUsersListChanged(val newAuthorizedUsersList: List<Repository.AuthorizedUser>) :
        Intent
}

data class AuthorizationScreenModel(
    val nameField: String = "",
    val surNameField: String = "",
    val patronymicField: String = "",
    val sexField: String = "",
    val subscribedCheckbox: Boolean = false,
    val authorizedUsersList: List<Repository.AuthorizedUser> = listOf()
)