package com.example.loginpage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.database.model.User
import com.example.loginpage.database.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun getUser(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUser(email, password)
            onResult(user)
        }
    }
}
