package com.example.team25.ui.reservation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.team25.R
import com.example.team25.databinding.FragmentReservationStep3Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationStep3Fragment : Fragment() {
    private var _binding: FragmentReservationStep3Binding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private var patientRelation = "본인"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setRelationDropDown()
        restoreForm()
        navigateToPrevious()
        navigateToNext()
    }

    private fun restoreForm() {
        val savedRelation = reservationInfoViewModel.reservationInfo.value.patient.patientRelation
        binding.relationAutoCompleteTextView.setText(savedRelation, false)
        patientRelation = savedRelation
    }

    private fun setRelationDropDown() {
        val relationOptions = resources.getStringArray(R.array.relation_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, relationOptions)
        binding.relationAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.relationAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            patientRelation = parent.getItemAtPosition(position).toString()
            reservationInfoViewModel.updatePatientRelation(patientRelation)
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
                .replace(R.id.fragment_container_view, ReservationStep4Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        setRelationDropDown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
