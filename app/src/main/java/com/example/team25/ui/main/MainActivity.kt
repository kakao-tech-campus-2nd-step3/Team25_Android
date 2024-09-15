package com.example.team25.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.ui.main.companion.LiveCompanionActivity
import com.example.team25.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToLiveCompanion()
    }

    private fun navigateToLiveCompanion() {
        binding.realTimeCompanionSeeAllBtn.setOnClickListener {
            val intent = Intent(this, LiveCompanionActivity::class.java)
            startActivity(intent)
        }
    }
}
