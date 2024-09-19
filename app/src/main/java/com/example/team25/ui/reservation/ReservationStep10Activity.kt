package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityReservationStep10Binding

class ReservationStep10Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep10Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep10Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.name.text = "김지수"
        navigateToMain()
        navigateToPrevious()
        navigateToNext()
    }

    private fun navigateToMain() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this@ReservationStep10Activity, ReservationStep11Activity::class.java)
            startActivity(intent)
        }
    }
}
