package com.example.authorizationtest

class AuthorizationScreenReducer {
    fun reduceAuthorizationScreenModel(model: AuthorizationScreenModel, intent: Intent) =
        model.reduce(intent)

    private fun AuthorizationScreenModel.reduce(intent: Intent) = when (intent) {
        is Intent.NameFieldChanged -> nameFieldChanged(intent)
        is Intent.SurnameFieldChanged -> surnameFieldChanged(intent)
        is Intent.PatronymicFieldChanged -> patronymicFieldChanged(intent)
        is Intent.SexFieldChanged -> sexFieldChanged(intent)
        is Intent.SubscribedCheckboxChanged -> subscribedCheckboxChanged(intent)
        is Intent.AuthorizedUsersListChanged -> authorizedUsersListChanged(intent)
    }

    private fun AuthorizationScreenModel.authorizedUsersListChanged(
        intent: Intent.AuthorizedUsersListChanged
    ) = copy(
        authorizedUsersList = intent.newAuthorizedUsersList
    )

    private fun AuthorizationScreenModel.nameFieldChanged(
        intent: Intent.NameFieldChanged
    ) = copy(
        nameField = intent.newName
    )

    private fun AuthorizationScreenModel.surnameFieldChanged(
        intent: Intent.SurnameFieldChanged
    ) = copy(
        surNameField = intent.newSurname
    )

    private fun AuthorizationScreenModel.patronymicFieldChanged(
        intent: Intent.PatronymicFieldChanged
    ) = copy(
        patronymicField = intent.newPatronymic
    )

    private fun AuthorizationScreenModel.sexFieldChanged(
        intent: Intent.SexFieldChanged
    ) = copy(
        sexField = intent.newSex
    )

    private fun AuthorizationScreenModel.subscribedCheckboxChanged(
        intent: Intent.SubscribedCheckboxChanged
    ) = copy(
        subscribedCheckbox = intent.newCheckboxSelection
    )
}