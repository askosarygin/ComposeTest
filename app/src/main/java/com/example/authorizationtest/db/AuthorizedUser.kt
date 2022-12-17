package com.example.authorizationtest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.authorizationtest.ServiceLocator

@Entity(tableName = ServiceLocator.authorizedUsersTableName)
data class AuthorizedUser(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "time_of_registration") val timeOfRegistration: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "patronymic") val patronymic: String,
    @ColumnInfo(name = "sex") val sex: String,
    @ColumnInfo(name = "subscribedCheckbox") val subscribedCheckbox: Boolean
)