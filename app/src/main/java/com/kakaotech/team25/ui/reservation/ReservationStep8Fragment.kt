package com.kakaotech.team25.ui.reservation

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep8Binding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ReservationStep8Fragment : Fragment() {
    private var _binding: FragmentReservationStep8Binding? = null
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private val binding get() = _binding!!
    private var curYear = 0
    private var curMonth = 0
    private var curDay = 0
    private var h = 0
    private var m = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep8Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentTime()
        setDatePicker()
        setTimePicker()
        navigateToPrevious()
        navigateToNext()
    }

    private fun getCurrentTime() {
        val currentTime = Calendar.getInstance()
        h = currentTime.get(Calendar.HOUR_OF_DAY)
        m = currentTime.get(Calendar.MINUTE)

        val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }
        curYear = tomorrow.get(Calendar.YEAR)
        curMonth = tomorrow.get(Calendar.MONTH) + 1
        curDay = tomorrow.get(Calendar.DAY_OF_MONTH)

        binding.yearTextView.text = curYear.toString()
        binding.monthTextView.text = curMonth.toString()
        binding.dayTextView.text = curDay.toString()
        reservationInfoViewModel.updateServiceDate(curYear, curMonth, curDay, h, m)
    }

    private fun setDatePicker() {
        binding.changeDayBtn.setOnClickListener {
            val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.timeInMillis
            val oneMonthLater = Calendar.getInstance().apply { add(Calendar.MONTH, 1) }.timeInMillis

            val constraintsBuilder = CalendarConstraints.Builder()
                .setStart(tomorrow)
                .setEnd(oneMonthLater)
                .setValidator(DateValidatorPointForward.from(tomorrow))

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(tomorrow)
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = Calendar.getInstance().apply {
                    timeInMillis = selection
                }
                curYear = selectedDate.get(Calendar.YEAR)
                curMonth = selectedDate.get(Calendar.MONTH) + 1
                curDay = selectedDate.get(Calendar.DAY_OF_MONTH)

                binding.yearTextView.text = curYear.toString()
                binding.monthTextView.text = curMonth.toString()
                binding.dayTextView.text = curDay.toString()

            }

            datePicker.show(parentFragmentManager, "DATE_PICKER")
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

        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            reservationInfoViewModel.updateServiceDate(curYear, curMonth, curDay, h, m)
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
