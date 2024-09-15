package com.example.team25.ui.reservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityReservationStep11Binding

class ReservationStep11Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep11Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep11Binding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()

    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
