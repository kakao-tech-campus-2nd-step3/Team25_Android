package com.example.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep3Binding

class ReservationStep3Fragment : Fragment() {
    private var _binding: FragmentReservationStep3Binding? = null
    private val binding get() = _binding!!
    private var relation = "본인"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRelationDropDown()
        navigateToPrevious()
        navigateToNext()
    }

    private fun setRelationDropDown() {
        val relationOptions = resources.getStringArray(R.array.relation_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, relationOptions)
        binding.relationAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.relationAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            relation = parent.getItemAtPosition(position).toString()
            Toast.makeText(requireContext(), "선택된 값: $relation", Toast.LENGTH_SHORT).show()
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
                .replace(R.id.fragment_container_view, ReservationStep4Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
