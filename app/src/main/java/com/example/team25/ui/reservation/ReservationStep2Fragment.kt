package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep2Binding

class ReservationStep2Fragment : Fragment() {
    private var _binding: FragmentReservationStep2Binding? = null
    private val binding get() = _binding!!
    private var service = "외래진료"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setServiceDropDown()
        navigateToPrevious()
    }

    private fun setServiceDropDown() {
        val serviceOptions = resources.getStringArray(R.array.service_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, serviceOptions)
        binding.serviceAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.serviceAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            service = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "선택된 값: $service", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
