package com.kakaotech.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
        observeReserveStatus()
        loadInfo()
        setReservationClickListener()
    }

    private fun setReservationClickListener() {
        binding.nextBtn.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun observeReserveStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationInfoViewModel.reserveStatus.collect { status ->
                    when (status) {
                        ReserveStatus.SUCCESS -> {
                            reservationInfoViewModel.updateReserveStatus(ReserveStatus.DEFAULT)
                            Toast.makeText(requireContext(), "예약 완료", Toast.LENGTH_SHORT).show()
                            navigateToMainActivity()
                        }
                        ReserveStatus.FAILURE -> {
                            reservationInfoViewModel.updateReserveStatus(ReserveStatus.DEFAULT)
                            Toast.makeText(requireContext(), "예약 실패", Toast.LENGTH_SHORT).show()
                        }
                        ReserveStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
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

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("예약 확인")
        builder.setMessage("예약을 진행하시겠습니까?")
        builder.setPositiveButton("확인") { _, _ ->
            reservationInfoViewModel.reserve()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
