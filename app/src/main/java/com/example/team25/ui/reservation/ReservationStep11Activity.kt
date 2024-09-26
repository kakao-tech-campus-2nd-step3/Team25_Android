package com.example.team25.ui.reservation

import android.content.Intent
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
        navigateToNext()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        val intent = Intent(this, ReservationCheckActivity::class.java)
        binding.selectWalkingLayout.setOnClickListener {
            intent.putExtra("vehicle", "도보")
            startActivity(intent)
        }

        binding.selectTaxiLayout.setOnClickListener {
            intent.putExtra("vehicle", "택시")
            startActivity(intent)
        }

        binding.selectPublicTransportationLayout.setOnClickListener {
            intent.putExtra("vehicle", "대중교통")
            startActivity(intent)
        }
    }
}
