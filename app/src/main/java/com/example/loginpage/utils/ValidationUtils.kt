package com.example.loginpage.utils

import android.util.Patterns

object ValidationUtils {

    fun validateName(name: String): Boolean {
        return name.isNotEmpty() && name.length in 3..24
    }

    fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return confirmPassword.isNotEmpty() && password == confirmPassword
    }
}
