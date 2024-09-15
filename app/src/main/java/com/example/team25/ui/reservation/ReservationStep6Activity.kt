package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityReservationStep6Binding

class ReservationStep6Activity : AppCompatActivity() {
    lateinit var binding: ActivityReservationStep6Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep6Binding.inflate(layoutInflater)
        setContentView(binding.root)

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
            val intent = Intent(this, ReservationStep7Activity::class.java)
            startActivity(intent)
        }
    }
}
