package com.example.loginpage.form

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.loginpage.databinding.ActivityCreateaccountFormBinding
import com.example.loginpage.details.CreateFromDetailsActivity
import java.util.Calendar

class CreateAccountFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityCreateaccountFormBinding
    private val sharedPrefsName = "user_prefs"
    private val savedData = "Data saved successfully!"
    private val telugu = "Telugu"
    private val hindi = "Hindi"
    private val english = "English"
    private val successfully = "Account Created Successfully"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateaccountFormBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        setContentView(binding.root)

        // Load saved data
        loadPreferences()

        // Set click listeners
        binding.btnVerify.setOnClickListener(this)
        binding.etDateOfBirth.setOnClickListener { showDatePicker() }
    }

    private fun validateInputs(
        name: String, surname: String, fatherName: String,
        dob: String, gender: String, aadharStatus: String
    ): Boolean {
        if (name.isEmpty() || surname.isEmpty() || fatherName.isEmpty() || dob.isEmpty() || gender.isEmpty() || aadharStatus.isEmpty()) {
            showToast("Please fill all the fields.")
            return false
        }
        if (name.length !in 3..24) {
            showToast("Name must be between 3 and 24 characters")
            return false
        }
        if (surname.length !in 3..24) {
            showToast("Surname must be between 3 and 24 characters")
            return false
        }
        if (fatherName.length !in 3..24) {
            showToast("Father's Name must be between 3 and 24 characters")
            return false
        }
        return true
    }

    private fun saveToPreferences(
        name: String, surname: String, fatherName: String,
        dob: String, gender: String, languages: String, aadharStatus: String
    ) {
        sharedPreferences.edit {
            putString("Name", name)
            putString("Surname", surname)
            putString("FatherName", fatherName)
            putString("DateOfBirth", dob)
            putString("Gender", gender)
            putString("Languages", languages)
            putString("AadharStatus", aadharStatus)
            apply()
        }
        showToast(savedData)
    }

    private fun navigateToDetails() {
        val name = binding.etName.text.toString().trim()
        val surname = binding.etsurname.text.toString().trim()
        val fatherName = binding.etfathername.text.toString().trim()
        val dob = binding.etDateOfBirth.text.toString().trim()

        val selectedGenderId = binding.rgGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            findViewById<RadioButton>(selectedGenderId).text.toString()
        } else {
            ""
        }

        val selectedAadharId = binding.rgAadhar.checkedRadioButtonId
        val aadharStatus = if (selectedAadharId != -1) {
            findViewById<RadioButton>(selectedAadharId).text.toString()
        } else {
            ""
        }

        // Fetch selected languages
        val languages = mutableListOf<String>()
        if (binding.cbTelugu.isChecked) languages.add(telugu)
        if (binding.cbHindi.isChecked) languages.add(hindi)
        if (binding.cbEnglish.isChecked) languages.add(english)
        val languagesKnown = languages.joinToString(", ")

        if (validateInputs(name, surname, fatherName, dob, gender, aadharStatus)) {
            saveToPreferences(name, surname, fatherName, dob, gender, languagesKnown, aadharStatus)
            Toast.makeText(this, successfully, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CreateFromDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadPreferences() {
        val name = sharedPreferences.getString("Name", "")
        val surname = sharedPreferences.getString("Surname", "")
        val fatherName = sharedPreferences.getString("FatherName", "")
        val dob = sharedPreferences.getString("DateOfBirth", "")
        val gender = sharedPreferences.getString("Gender", "")
        val languages = sharedPreferences.getString("Languages", "")
        val aadharStatus = sharedPreferences.getString("AadharStatus", "")

        binding.etName.setText(name)
        binding.etsurname.setText(surname)
        binding.etfathername.setText(fatherName)
        binding.etDateOfBirth.setText(dob)

        when (gender) {
            "Male" -> binding.rbMale.isChecked = true
            "Female" -> binding.rbFemale.isChecked = true
            "Other" -> binding.rbOther.isChecked = true
        }

        // Restore selected languages
        if (languages?.contains(telugu) == true) binding.cbTelugu.isChecked = true
        if (languages?.contains(hindi) == true) binding.cbHindi.isChecked = true
        if (languages?.contains(english) == true) binding.cbEnglish.isChecked = true

        when (aadharStatus) {
            "Yes" -> binding.rbAadharYes.isChecked = true
            "No" -> binding.rbAadharNo.isChecked = true
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.etDateOfBirth.setText(formattedDate)
        }, year, month, day)

        datePicker.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnVerify.id -> {
                navigateToDetails()
            }
        }
    }
}
