package com.example.loginpage.database.repository

import com.example.loginpage.database.dao.UserDao
import com.example.loginpage.database.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUser(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }
}
