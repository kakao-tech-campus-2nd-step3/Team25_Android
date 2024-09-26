package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep4Binding

class ReservationStep4Fragment : Fragment() {
    private var _binding: FragmentReservationStep4Binding? = null
    private val binding get() = _binding!!
    private var firstPhoneNum = "010"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPhoneNumDropDown()
        navigateToPrevious()
        navigateToNext()
    }

    private fun setPhoneNumDropDown() {
        val phoneNumOptions = resources.getStringArray(R.array.phone_num_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, phoneNumOptions)
        binding.phoneNumAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.phoneNumAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            firstPhoneNum = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "선택된 값: $firstPhoneNum", Toast.LENGTH_SHORT).show()
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
                .replace(R.id.fragment_container_view, ReservationStep5Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
