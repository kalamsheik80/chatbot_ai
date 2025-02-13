package com.example.loginpage.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.MainActivity
import com.example.loginpage.databinding.DashboardactivityBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: DashboardactivityBinding
    private val sharedPrefsName = "user_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding

        binding = DashboardactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check login status
        val sharedPreferences = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // Redirect to LoginActivity if not logged in
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Set onClickListener for View Profile button
        binding.btnProfile.setOnClickListener {
            showToast("View Profile clicked")
        }

        // Set onClickListener for Settings button
        binding.btnSettings.setOnClickListener {
            showToast("Settings clicked")
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
