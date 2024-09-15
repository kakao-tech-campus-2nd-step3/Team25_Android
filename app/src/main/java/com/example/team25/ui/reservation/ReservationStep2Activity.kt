package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationStep2Binding

class ReservationStep2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep2Binding
    private var service = "외래진료"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setServiceDropDown()
        navigateToPrevious()
        navigateToNext()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, ReservationStep3Activity::class.java)
            startActivity(intent)
        }
    }

    private fun setServiceDropDown() {
        val serviceOptions = resources.getStringArray(R.array.service_option)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, serviceOptions)
        binding.serviceAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.serviceAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            service = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "선택된 값: $service", Toast.LENGTH_SHORT).show()
        }
    }
}
