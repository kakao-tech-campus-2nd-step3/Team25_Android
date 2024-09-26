package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep5Binding
import java.time.LocalDate
import java.time.format.DateTimeParseException

class ReservationStep5Fragment : Fragment() {
    private var _binding: FragmentReservationStep5Binding? = null
    private val binding get() = _binding!!
    private var birthday: LocalDate? = null

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
            parentFragmentManager.popBackStack()
        }

        binding.previousBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToNext() {
        binding.nextBtn.setOnClickListener {
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
