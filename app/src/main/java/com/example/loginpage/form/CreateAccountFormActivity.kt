package com.example.loginpage.form

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.loginpage.databinding.ActivityCreateaccountFormBinding
import com.example.loginpage.details.CreateFromDetailsActivity
import com.example.loginpage.utils.ValidationUtils
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

        binding.btnVerify.setOnClickListener(this)
        binding.etDateOfBirth.setOnClickListener { showDatePicker() }
    }

    private fun saveToPreferences(
        name: String,
        surname: String,
        fatherName: String,
        dob: String,
        gender: String,
        languages: String,
        aadharStatus: String
    ) {
        Log.e("aadharStatus", aadharStatus)
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
        val gender =
            if (selectedGenderId != -1) findViewById<RadioButton>(selectedGenderId).text.toString() else ""
        val selectedAadharId = binding.rgAadhar.checkedRadioButtonId
        val aadharStatus =
            if (selectedAadharId != -1) findViewById<RadioButton>(selectedAadharId).text.toString() else ""

        val languages = mutableListOf<String>()
        if (binding.cbTelugu.isChecked) languages.add(telugu)
        if (binding.cbHindi.isChecked) languages.add(hindi)
        if (binding.cbEnglish.isChecked) languages.add(english)
        val languagesKnown = languages.joinToString(", ")


        if (!ValidationUtils.validateName(name)) {
            showToast("Invalid Name: Must be between 3 and 24 characters")
            return
        }
        if (!ValidationUtils.validateSurname(surname)) {
            showToast("Invalid Surname: Must be between 3 and 24 characters")
            return
        }
        if (!ValidationUtils.validateFatherName(fatherName)) {
            showToast("Invalid Father's Name: Must be between 3 and 24 characters")
            return
        }

        saveToPreferences(name, surname, fatherName, dob, gender, languagesKnown, aadharStatus)
        Toast.makeText(this, successfully, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, CreateFromDetailsActivity::class.java))
    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                binding.etDateOfBirth.setText("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        if (v?.id == binding.btnVerify.id) {
            navigateToDetails()
        }
    }
}
