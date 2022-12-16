package com.example.authorizationtest

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.text.SimpleDateFormat
import java.util.*

class Interactor {
    private val repository = Repository()
    private val mutex = Mutex()
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val dateFormat = SimpleDateFormat("HH:mm")

    val modelFlow = MutableStateFlow(InteractorModel())

    init {
        ioScope.launch {
            val data = repository.downloadAuthorizedUsersDB()
            updateModel {
                it.copy(authorizedUsersList = mutableStateListOf<AuthorizedUser>().apply {
                    addAll(data)
                })
            }
        }
    }

    private fun updateModel(onUpdate: (InteractorModel) -> InteractorModel) {
        runBlocking {
            mutex.withLock {
                onUpdate(modelFlow.value).apply {
                    modelFlow.value = this
                }
            }
            Log.i("MY_TAG", "Модель интерактора обновлена")
        }
    }

    fun addNewAuthorizedUser(
        surname: String,
        name: String,
        patronymic: String,
        sex: String,
        subscribedCheckbox: Boolean
    ) {
        ioScope.launch {
            val timeOfRegistration = dateFormat.format(Calendar.getInstance().timeInMillis)
            updateModel { interactorModel ->
                interactorModel.copy().apply {
                    authorizedUsersList.add(
                        AuthorizedUser(
                            if (this.authorizedUsersList.isNotEmpty()) this.authorizedUsersList.last().id + 1L else 0L,
                            timeOfRegistration,
                            surname,
                            name,
                            patronymic,
                            sex,
                            subscribedCheckbox
                        )
                    )
                }
            }

            repository.addAuthorizedUserToDB(
                AuthorizedUser(
                    0L,
                    timeOfRegistration,
                    surname,
                    name,
                    patronymic,
                    sex,
                    subscribedCheckbox
                )
            )
        }
    }
}

data class InteractorModel(
    val authorizedUsersList: SnapshotStateList<AuthorizedUser> = mutableStateListOf()
)