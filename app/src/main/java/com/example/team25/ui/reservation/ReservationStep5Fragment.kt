package com.example.team25.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep5Binding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeParseException

@AndroidEntryPoint
class ReservationStep5Fragment : Fragment() {
    private var _binding: FragmentReservationStep5Binding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private var birthday: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setBirthdayTextChangedListener()
        restoreForm()
        navigateToPrevious()
        navigateToNext()
    }

    private fun restoreForm() {
        birthday = reservationInfoViewModel.reservationInfo.value.patient.patientBirth

        if (birthday.isNotEmpty()) {
            val year = birthday.substring(0, 4)
            val month = birthday.substring(4, 6)
            val day = birthday.substring(6)

            binding.yearInput.setText(year)
            binding.monthInput.setText(month)
            binding.dayInput.setText(day)
            updateDate()
        }
    }

    private fun setBirthdayTextChangedListener() {
        binding.yearInput.addTextChangedListener { text ->
            updateDate()
        }

        binding.monthInput.addTextChangedListener { text ->
            updateDate()
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
            birthday = "$year${month.padStart(2, '0')}${day.padStart(2, '0')}"
            reservationInfoViewModel.updatePatientBirth(birthday)
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.previousBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
            Log.d("ReservationInfo", reservationInfoViewModel.reservationInfo.value.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep6Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
