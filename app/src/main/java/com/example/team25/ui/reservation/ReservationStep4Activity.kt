package com.example.team25.ui.reservation

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationStep4Binding

class ReservationStep4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep4Binding
    private var firstPhoneNum = "010"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setPhoneNumDropDown()
        navigateToPrevious()
    }

    private fun setPhoneNumDropDown() {
        val phoneNumOptions = resources.getStringArray(R.array.phone_num_option)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, phoneNumOptions)
        binding.phoneNumAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.phoneNumAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            firstPhoneNum = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "선택된 값: $firstPhoneNum", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
