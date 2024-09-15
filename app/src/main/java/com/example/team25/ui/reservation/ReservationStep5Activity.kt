package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.team25.databinding.ActivityReservationStep5Binding
import java.time.LocalDate
import java.time.format.DateTimeParseException

class ReservationStep5Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep5Binding
    private var birthday: LocalDate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setBirthdayTextChangedListener()
        navigateToPrevious()
        navigateToNext()
    }

    private fun setBirthdayTextChangedListener() {
        binding.yearInput.addTextChangedListener { text ->
            updateDate()
            if (text?.length == 4) {
                binding.monthInput.requestFocus()
            }
        }

        binding.monthInput.addTextChangedListener { text ->
            updateDate()
            if (text?.length == 2) {
                binding.dayInput.requestFocus()
            }
        }

        binding.dayInput.addTextChangedListener { text ->
            updateDate()
        }
    }

    private fun updateDate() {
        val year = binding.yearInput.text.toString()
        val month = binding.monthInput.text.toString()
        val day = binding.dayInput.text.toString()

        if (year.length == 4 && month.isNotEmpty() && day.isNotEmpty()) {
            try {
                birthday = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
                println("Selected date: $birthday")
            } catch (e: DateTimeParseException) {
                e.printStackTrace()
            }
        }
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
            val intent = Intent(this, ReservationStep6Activity::class.java)
            startActivity(intent)
        }
    }
}
