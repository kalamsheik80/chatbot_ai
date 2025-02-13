package com.example.loginpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.dashboard.DashboardActivity
import com.example.loginpage.databinding.ActivityMainBinding
import com.example.loginpage.form.CreateAccountFormActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Mock credentials
    private val correctUsername = "admin"
    private val correctPassword = "pass@123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        val fruitName = "Apple"
//        println("fruit name : $fruitName")
//        val user = User("Naga Sai", "L", "25", "9645455445", "nagasai.l@gmail.com")
//        println("user name: ${user.name}")

        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

        // Check if user is already logged in
        binding.loginBtn.setOnClickListener {
            val usernameInput = binding.etUserName.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            Log.e("Login Details", "Username: $usernameInput - Password: $passwordInput")

            when {
                usernameInput.isEmpty() && passwordInput.isEmpty() -> {
                    showToast("Error: Username and Password cannot be empty")
                }

                usernameInput.isEmpty() -> {
                    showToast("Error: Username cannot be empty")
                }

                passwordInput.isEmpty() -> {
                    showToast("Error: Password cannot be empty")
                }

                usernameInput != correctUsername && passwordInput == correctPassword -> {
                    showToast("Incorrect Username")

                }

                usernameInput == correctUsername && passwordInput != correctPassword -> {
                    showToast("Incorrect Password")

                }

                usernameInput == correctUsername && passwordInput == correctPassword -> {
                    showToast("Login Successful")

                    navigateToDashboard()
                }

                else -> {
                    showToast("Incorrect Username and Password")

                }
            }
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish() // Close login activity so user can't go back
    }

    fun openCreateAccount(view: View?) {
        val intent = Intent(
            this,
            CreateAccountFormActivity::class.java
        )
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
