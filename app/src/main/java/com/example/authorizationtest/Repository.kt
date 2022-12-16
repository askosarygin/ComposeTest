package com.example.authorizationtest

class Repository {
    private val authorizedUsersDB = mutableListOf<AuthorizedUser>()

    suspend fun downloadAuthorizedUsersDB() = authorizedUsersDB

    suspend fun addAuthorizedUserToDB(authorizedUser: AuthorizedUser) {
        authorizedUsersDB.add(authorizedUser)
    }
}

data class AuthorizedUser(
    val id: Long,
    val timeOfRegistration: String,
    val surname: String,
    val name: String,
    val patronymic: String,
    val sex: String,
    val subscribedCheckbox: Boolean
)