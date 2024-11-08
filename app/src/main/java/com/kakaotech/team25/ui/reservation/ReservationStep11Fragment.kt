package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationStep11Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationStep11Fragment : Fragment() {
    private var _binding: FragmentReservationStep11Binding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationStep11Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        navigateToPrevious()
        navigateToNext()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToNext() {
        binding.selectWalkingLayout.setOnClickListener {
            reservationInfoViewModel.updateTransportation("도보")
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationCheckFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.selectTaxiLayout.setOnClickListener {
            reservationInfoViewModel.updateTransportation("택시")
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationCheckFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.selectPublicTransportationLayout.setOnClickListener {
            reservationInfoViewModel.updateTransportation("대중교통")
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationCheckFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
