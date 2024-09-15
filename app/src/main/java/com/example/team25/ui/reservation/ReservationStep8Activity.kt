package com.example.team25.ui.reservation

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.databinding.ActivityReservationStep8Binding
import java.util.Calendar

class ReservationStep8Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep8Binding
    private var h = 0
    private var m = 0
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        getCurrentTime()
        getCurrentDate()
        setDatePicker()
        setTimePicker()
        navigateToPrevious()
        navigateToNext()
    }

    private fun getCurrentTime() {
        val currentTime = Calendar.getInstance()
        h = currentTime.get(Calendar.HOUR_OF_DAY)
        m = currentTime.get(Calendar.MINUTE)
    }

    private fun getCurrentDate() {
        val currentDate = Calendar.getInstance()
        selectedYear = currentDate.get(Calendar.YEAR)
        selectedMonth = currentDate.get(Calendar.MONTH)
        selectedDay = currentDate.get(Calendar.DAY_OF_MONTH)
    }

    private fun setDatePicker() {
        // 내일 날짜를 계산합니다.
        val tomorrow =
            Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 1)
            }

        val year = tomorrow.get(Calendar.YEAR)
        val month = tomorrow.get(Calendar.MONTH)
        val day = tomorrow.get(Calendar.DAY_OF_MONTH)

        binding.datePicker.minDate = tomorrow.timeInMillis
        val oneMonthLater =
            Calendar.getInstance().apply {
                add(Calendar.MONTH, 1)
            }
        binding.datePicker.maxDate = oneMonthLater.timeInMillis

        binding.datePicker.init(
            year,
            month,
            day,
        ) { _, selectedYear, selectedMonth, selectedDay ->
            this.selectedYear = selectedYear
            this.selectedMonth = selectedMonth
            this.selectedDay = selectedDay

            Toast.makeText(
                this,
                "Selected Date: $selectedYear/${selectedMonth + 1}/$selectedDay",
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun setTimePicker() {
        val minutePickerId = Resources.getSystem().getIdentifier("minute", "id", "android")
        val minutePicker = binding.timePicker.findViewById<NumberPicker>(minutePickerId)

        minutePicker.minValue = 0
        minutePicker.maxValue = 5
        minutePicker.displayedValues = arrayOf("00", "10", "20", "30", "40", "50")

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            h = hourOfDay
            m = minute * 10

            Toast.makeText(this, "Selected Time: $h:$m", Toast.LENGTH_SHORT).show()
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
            val intent = Intent(this, ReservationStep9Activity::class.java)
            startActivity(intent)
        }
    }
}
