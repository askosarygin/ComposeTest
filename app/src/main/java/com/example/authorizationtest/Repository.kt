package com.example.authorizationtest

class Repository {
    private val authorizedUsersDB = mutableListOf<AuthorizedUser>()

    private fun downloadAuthorizedUsersDB() = authorizedUsersDB

    fun addAuthorizedUserToDB(
        surname: String,
        name: String,
        patronymic: String,
        sex: String,
        subscribedCheckbox: String
    ): List<AuthorizedUser> {
        authorizedUsersDB.add(
            AuthorizedUser(
                surname,
                name,
                patronymic,
                sex,
                subscribedCheckbox
            )
        )
        return downloadAuthorizedUsersDB()
    }

    class AuthorizedUser(
        val surname: String,
        val name: String,
        val patronymic: String,
        val sex: String,
        val subscribedCheckbox: String
    )
}