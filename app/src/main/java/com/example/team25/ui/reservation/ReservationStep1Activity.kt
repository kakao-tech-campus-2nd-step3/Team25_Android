package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationStep1Binding

class ReservationStep1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep1Binding
    private var firstPhoneNum = "010"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToMain()
        navigateToNext()
        setPhoneNumDropDown()
    }

    private fun navigateToMain() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, ReservationStep2Activity::class.java)
            startActivity(intent)
        }
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
}
