package com.example.authorizationtest

class Interactor {
    private val repository = Repository()

    fun addAuthorizedUser(
        surname: String,
        name: String,
        patronymic: String,
        sex: String,
        subscribedCheckbox: Boolean
    ): List<Repository.AuthorizedUser> =
        repository.addAuthorizedUserToDB(
            surname,
            name,
            patronymic,
            sex,
            if (subscribedCheckbox) "оформлена" else "не оформлена"
        )
}