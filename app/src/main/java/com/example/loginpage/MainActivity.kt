package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.loginpage.dashboard.ChatbotActivity
import com.example.loginpage.database.AppDatabase
import com.example.loginpage.databinding.ActivityMainBinding
import com.example.loginpage.form.CreateAccountFormActivity
import com.example.loginpage.form.ForgetPasswordActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room Database
        database = AppDatabase.getDatabase(this)

        binding.loginBtn.setOnClickListener {
            val email = binding.etUserName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty()) {
                showToast("Please enter email")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                showToast("Please enter password")
                return@setOnClickListener
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val userDao = database.userDao()
                val user = userDao.getUser(email, password)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        showToast("Login Successful")
                        navigateToDashboard()
                    } else {
                        showToast("Invalid Email or Password")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("An error occurred: ${e.message}")
                }
            }
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, ChatbotActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun openForgetPassword(view: View) {
        val intent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(intent)
    }

    fun openCreateAccount(view: View) {
        val intent = Intent(this, CreateAccountFormActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
