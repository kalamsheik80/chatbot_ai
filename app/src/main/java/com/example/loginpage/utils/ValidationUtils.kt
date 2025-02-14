package com.example.loginpage.utils

object ValidationUtils {

    fun validateName(name: String): Boolean {
        var res = true
        if (name.isEmpty()) {
            res = false
        } else if (name.length !in 3..24) {
            res = false
        }
        return res
    }

    fun validateSurname(surname: String): Boolean {
        var res = true
        if (surname.isEmpty()) {
            res = false
        } else if (surname.length !in 3..24) {
            res = false
        }
        return res
    }

    fun validateFatherName(fatherName: String): Boolean {
        var res = true
        if (fatherName.isEmpty()) {
            res = false
        } else if (fatherName.length !in 3..24) {
            res = false
        }
        return res
    }
}
