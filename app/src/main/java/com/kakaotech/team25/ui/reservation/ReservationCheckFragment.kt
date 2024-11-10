package com.kakaotech.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.FragmentReservationCheckBinding
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.ui.main.MainActivity
import kotlinx.coroutines.launch

class ReservationCheckFragment : Fragment() {
    private var _binding: FragmentReservationCheckBinding? = null
    private val binding get() = _binding!!
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()
    private lateinit var reservationInfo: ReservationInfo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        navigateToPrevious()
        loadInfo()
        navigateToPay()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun navigateToPay() {
        binding.nextBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationPaymentFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun loadInfo() {
        reservationInfo = reservationInfoViewModel.getReservationInfo()

        binding.patientNameTextView.text = reservationInfo.patient.patientName
        when (reservationInfo.patient.patientGender) {
            Gender.MALE -> binding.patientGenderTextView.text = "남성"
            Gender.FEMALE -> binding.patientGender.text = "여성"
            else -> binding.patientGender.text = "오류"
        }
        binding.patientPhoneNumTextView.text = reservationInfo.patient.patientPhone
        binding.fromLocationTextView.text = reservationInfo.departureLocation
        binding.toLocationTextView.text = reservationInfo.arrivalLocation
        binding.managerNameTextView.text = reservationInfo.managerName
        binding.selectedVehicleTextView.text = reservationInfo.transportation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
