package com.example.authorizationtest

import com.example.authorizationtest.db.AuthorizedUser
import com.example.authorizationtest.db.AuthorizedUserDAO

class Repository(authorizedUsersDB: AuthorizedUserDAO) {
    private val authorizedUsersDB = authorizedUsersDB

    suspend fun downloadAuthorizedUsersDB() = authorizedUsersDB.getAll()

    suspend fun addAuthorizedUserToDB(authorizedUser: AuthorizedUser) {
        authorizedUsersDB.add(authorizedUser)
    }
}