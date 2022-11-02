package com.example.authorizationtest

import java.text.SimpleDateFormat
import java.util.*

class Repository {
    private val authorizedUsersDB = mutableListOf<AuthorizedUser>()

    private fun downloadAuthorizedUsersDB() = authorizedUsersDB

    private var idNumber = 0L

    private val dateFormat = SimpleDateFormat("HH:mm")

    fun addAuthorizedUserToDB(
        surname: String,
        name: String,
        patronymic: String,
        sex: String,
        subscribedCheckbox: String
    ): List<AuthorizedUser> {
        authorizedUsersDB.add(
            AuthorizedUser(
                idNumber,
                dateFormat.format(Calendar.getInstance().timeInMillis),
                surname,
                name,
                patronymic,
                sex,
                subscribedCheckbox
            )
        )
        idNumber += 1L
        return downloadAuthorizedUsersDB()
    }

    class AuthorizedUser(
        val id: Long,
        val timeOfRegistration: String,
        val surname: String,
        val name: String,
        val patronymic: String,
        val sex: String,
        val subscribedCheckbox: String
    )
}