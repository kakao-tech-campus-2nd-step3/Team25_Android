package com.example.team25.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityMainBinding
import com.example.team25.ui.login.LoginEntryActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToLogin()
    }

    private fun navigateToLogin() {
        binding.welcomeTextView.setOnClickListener {
            val intent = Intent(this, LoginEntryActivity::class.java)
            startActivity(intent)
        }
    }
}
