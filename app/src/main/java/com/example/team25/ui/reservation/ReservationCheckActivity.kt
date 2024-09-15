package com.example.team25.ui.reservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationCheckBinding

class ReservationCheckActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVehicle()
    }

    private fun setVehicle() {
        val vehicle = intent.getStringExtra("vehicle")
        binding.selectedVehicleTextView.text = vehicle ?: "이동 수단이 선택되지 않음"
    }

}
