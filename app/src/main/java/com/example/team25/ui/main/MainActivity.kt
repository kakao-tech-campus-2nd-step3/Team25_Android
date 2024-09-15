package com.example.team25.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.ui.main.companion.LiveCompanionActivity
import com.example.team25.databinding.ActivityMainBinding
import com.example.team25.ui.login.LoginEntryActivity
import com.example.team25.ui.reservation.ReservationStep1Activity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToLiveCompanion()
        navigateToLogin()
        navigateToReservation()
    }

    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionSeeAllBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToLogin() {
        binding.welcomeTextView.setOnClickListener {
            val intent = Intent(this, LoginEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservation() {
        binding.goReservationBtn.setOnClickListener {
            val intent = Intent(this, ReservationStep1Activity::class.java)
            startActivity(intent)
        }
    }
}
