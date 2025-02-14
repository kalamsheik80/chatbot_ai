package com.example.loginpage.details

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivityCreateaccountDetailsBinding

class CreateFromDetailsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityCreateaccountDetailsBinding
    private val sharedPrefsName = "user_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCreateaccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(sharedPrefsName, MODE_PRIVATE)

        // Display User Details
        displayUserDetails()
    }

    private fun displayUserDetails() {
        val name = sharedPreferences.getString("Name", "N/A")
        val surname = sharedPreferences.getString("Surname", "N/A")
        val fatherName = sharedPreferences.getString("FatherName", "N/A")
        val dob = sharedPreferences.getString("DateOfBirth", "N/A")
        val gender = sharedPreferences.getString("Gender", "N/A")
        val languages = sharedPreferences.getString("Languages", "N/A")
        val aadharStatus = sharedPreferences.getString("AadharStatus", "N/A")

        binding.etName.text = name
        binding.etsurname.text = surname
        binding.etfathername.text = fatherName
        binding.etDateOfBirth.text = dob
        binding.etGender.text = gender
        binding.etLanguages.text = languages
        binding.etAadhar.text = aadharStatus
    }
}
