package com.example.team25.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.ui.main.companion.LiveCompanionActivity
import com.example.team25.databinding.ActivityMainBinding
import com.example.team25.ui.main.status.ReservationStatusActivity
import com.example.team25.ui.login.LoginEntryActivity
import com.example.team25.ui.reservation.ReservationStep1Activity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userNickname = intent.getStringExtra("user_nickname")
        if (userNickname != null) {
            // 사용자 정보를 UI에 설정하거나 다른 작업을 수행합니다.
            binding.welcomeTextView.text = "Welcome, $userNickname"
        }

        navigateToLiveCompanion()
        navigateToReservationStatus()
        navigateToLogin()
        navigateToReservation()
    }

    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionSeeAllBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToReservationStatus() {
        binding.reservationSeeAllBtn.setOnClickListener {
            val intent = Intent(this, ReservationStatusActivity::class.java)
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
