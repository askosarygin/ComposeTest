package com.example.authorizationtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.authorizationtest.ServiceLocator

@Dao
interface AuthorizedUserDAO {
    @Insert
    fun add(authorizedUser: AuthorizedUser)

    @Query("SELECT * FROM ${ServiceLocator.authorizedUsersTableName}")
    fun getAll() : List<AuthorizedUser>
}