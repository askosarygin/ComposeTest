package com.example.authorizationtest

import android.content.Context
import androidx.room.Room
import com.example.authorizationtest.db.AuthorizedUsersDB

class ServiceLocator(context: Context) {
    private val authorizedUsersDB = Room.databaseBuilder(
        context,
        AuthorizedUsersDB::class.java,
        authorizedUsersTableName
    ).build().getAuthorizedUsersDao()

    val interactor = Interactor(authorizedUsersDB)

    companion object {
        lateinit var instance: ServiceLocator
        const val authorizedUsersTableName = "authorized_users"
    }
}