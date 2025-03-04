package com.example.loginpage.form

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.R
import com.example.loginpage.network.ForgetPasswordRequest
import com.example.loginpage.network.ForgetPasswordResponse
import com.example.loginpage.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        emailEditText = findViewById(R.id.et_email)
        sendButton = findViewById(R.id.btn_send)

        sendButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (email.isNotEmpty()) {
                sendForgotPasswordRequest(email)
            } else {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendForgotPasswordRequest(email: String) {
        val request = ForgetPasswordRequest(email)

        RetrofitClient.instance.forgetPassword(request)
            .enqueue(object : Callback<ForgetPasswordResponse> {
                override fun onResponse(
                    call: Call<ForgetPasswordResponse>,
                    response: Response<ForgetPasswordResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(
                            applicationContext,
                            "Check your email for reset link",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            response.body()?.message ?: "Error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ForgetPasswordResponse>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Request Failed: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
