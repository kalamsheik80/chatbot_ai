package com.example.loginpage.dashboard

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.DashboardactivityBinding

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: DashboardactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = DashboardactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set onClickListener for Send button
        binding.btnSend.setOnClickListener {
            val userMessage = binding.etUserInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                sendMessage(userMessage)
                binding.etUserInput.text.clear()
            } else {
                showToast("Please enter a message")
            }
        }
    }

    private fun sendMessage(message: String) {
        // TODO: Implement chatbot logic
        showToast("Sent: $message")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
