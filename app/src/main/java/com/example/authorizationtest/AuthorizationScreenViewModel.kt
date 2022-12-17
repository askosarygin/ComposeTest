package com.example.authorizationtest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.authorizationtest.db.AuthorizedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthorizationScreenViewModel : ViewModel() {
    private val reducer = AuthorizationScreenReducer()
    private val mutex = Mutex()
    private val interactor = ServiceLocator.instance.interactor
    private val ioScope = CoroutineScope(Dispatchers.IO)

    val modelFlow = MutableStateFlow(AuthorizationScreenModel())

    init {
        ioScope.launch {
            interactor.modelFlow.collect { interactorModel ->
                onIntent(Intent.AuthorizedUsersListChanged(
                    interactorModel.authorizedUsersList
                ))
            }
        }
    }

    fun addNewAuthorizedUser(
        surname: String,
        name: String,
        patronymic: String,
        sex: String,
        subscribedCheckbox: Boolean
    ) {
        interactor.addNewAuthorizedUser(surname, name, patronymic, sex, subscribedCheckbox)
    }

    fun onIntent(intent: Intent) {
        runBlocking {
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
    class AuthorizedUsersListChanged(
        val newAuthorizedUsersList: SnapshotStateList<AuthorizedUser>
    ) : Intent
}

data class AuthorizationScreenModel(
    val nameField: String = "",
    val surNameField: String = "",
    val patronymicField: String = "",
    val sexField: String = "",
    val subscribedCheckbox: Boolean = false,
    val authorizedUsersList: SnapshotStateList<AuthorizedUser> = mutableStateListOf()
)