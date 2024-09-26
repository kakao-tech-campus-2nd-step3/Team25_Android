package com.example.team25.ui.reservation

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep8Binding
import java.util.Calendar

class ReservationStep8Fragment : Fragment() {
    private var _binding: FragmentReservationStep8Binding? = null
    private val binding get() = _binding!!
    private var h = 0
    private var m = 0
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep8Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        val year = tomorrow.get(Calendar.YEAR)
        val month = tomorrow.get(Calendar.MONTH)
        val day = tomorrow.get(Calendar.DAY_OF_MONTH)

        binding.datePicker.minDate = tomorrow.timeInMillis
        val oneMonthLater = Calendar.getInstance().apply {
            add(Calendar.MONTH, 1)
        }
        binding.datePicker.maxDate = oneMonthLater.timeInMillis

        binding.datePicker.init(
            year,
            month,
            day
        ) { _, selectedYear, selectedMonth, selectedDay ->
            this.selectedYear = selectedYear
            this.selectedMonth = selectedMonth
            this.selectedDay = selectedDay

            Toast.makeText(
                requireContext(),
                "Selected Date: $selectedYear/${selectedMonth + 1}/$selectedDay",
                Toast.LENGTH_SHORT
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

            Toast.makeText(requireContext(), "Selected Time: $h:$m", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep9Fragment())
                .addToBackStack(null)
                .commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
