package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep5Binding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

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
            if (text?.length == 4) {
                binding.monthInput.requestFocus()
            }
            updateDate()
        }

        binding.monthInput.addTextChangedListener { text ->
            if (text?.length == 2) {
                binding.dayInput.requestFocus()
            }
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
            birthday = "${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}"
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
            val year = binding.yearInput.text.toString()
            val month = binding.monthInput.text.toString()
            val day = binding.dayInput.text.toString()

            // 유효성 검사
            if (!isValidBirthday(year, month, day)) {
                Toast.makeText(requireContext(), "생일이 올바른 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("ReservationInfo", reservationInfoViewModel.reservationInfo.value.toString())

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep6Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun isValidBirthday(
        year: String,
        month: String,
        day: String,
    ): Boolean {
        if (year.length != 4 || year.toInt() < 1900 || year.toInt() > LocalDate.now().year) {
            return false
        }
        if (month.isEmpty() || month.toInt() < 1 || month.toInt() > 12) {
            return false
        }

        if (day.isEmpty() || day.toInt() < 1 || day.toInt() > 31) {
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
