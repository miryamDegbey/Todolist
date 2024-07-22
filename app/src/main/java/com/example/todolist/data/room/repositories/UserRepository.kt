package com.example.todolist.data.room.repositories

import com.example.todolist.data.room.daos.UserDao
import com.example.todolist.data.room.models.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao

) {
    val userList = userDao.usersList()

    suspend fun register(user: User) {userDao.register(user) }

    fun getUserByEmail(email: String): Flow<List<User>> = userDao.getUserByEmail(email)
    fun activateUserByEmail(email: String) = userDao.activateUserByEmail(email)
}