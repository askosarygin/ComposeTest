package com.example.authorizationtest.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AuthorizedUser::class], version = 1, exportSchema = false)
abstract class AuthorizedUsersDB : RoomDatabase() {
    abstract fun getAuthorizedUsersDao() : AuthorizedUserDAO
}