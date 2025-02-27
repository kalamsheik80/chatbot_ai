package com.example.loginpage.form

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.loginpage.MainActivity
import com.example.loginpage.database.AppDatabase
import com.example.loginpage.database.model.User
import com.example.loginpage.databinding.ActivityCreateaccountFormBinding
import com.example.loginpage.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCreateaccountFormBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateaccountFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room Database
        database = AppDatabase.getDatabase(this)

        binding.btnCreateAccount.setOnClickListener(this)
    }

    private fun saveUserToDatabase(name: String, email: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val userDao = database.userDao()
            val user = User(name = name, email = email, password = password)
            userDao.insertUser(user)

            runOnUiThread {
                showToast("Account Created Successfully!")
                navigateToLogin()
            }
        }
    }

    private fun navigateToDetails() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (!ValidationUtils.validateName(name)) {
            showToast("Invalid Name: Must be between 3 and 24 characters")
            return
        }
        if (!ValidationUtils.validateEmail(email)) {
            showToast("Invalid Email Format")
            return
        }
        if (!ValidationUtils.validatePassword(password)) {
            showToast("Password must be at least 6 characters")
            return
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return
        }

        // Save user to database
        saveUserToDatabase(name, email, password)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (v?.id == binding.btnCreateAccount.id) {
            navigateToDetails()
        }
    }
}
