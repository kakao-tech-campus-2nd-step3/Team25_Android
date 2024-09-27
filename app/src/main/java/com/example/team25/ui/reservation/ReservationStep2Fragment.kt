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
import com.example.team25.databinding.FragmentReservationStep2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationStep2Fragment : Fragment() {
    private var _binding: FragmentReservationStep2Binding? = null
    private val binding get() = _binding!!

    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private var serviceType = "외래진료"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setServiceDropDown()
        navigateToPrevious()
        navigateToNext()
    }

    private fun setServiceDropDown() {
        val serviceOptions = resources.getStringArray(R.array.service_option)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, serviceOptions)
        binding.serviceAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.serviceAutoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            serviceType = parent.getItemAtPosition(position).toString()
            reservationInfoViewModel.updateServiceType(serviceType)
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
                .replace(R.id.fragment_container_view, ReservationStep3Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        setServiceDropDown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
