package com.example.loginpage.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Request Model
data class ForgetPasswordRequest(
    val email: String
)

// Response Model
data class ForgetPasswordResponse(
    val success: Boolean,
    val message: String
)

// Retrofit Interface
interface ForgetPasswordApi {
    @POST("api/forgot-password")
    fun forgetPassword(@Body request: ForgetPasswordRequest): Call<ForgetPasswordResponse>
}
