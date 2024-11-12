package com.kakaotech.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.bold
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
        setPrivacyClickListener()
        setThirdPrivacyClickListener()
    }

    private fun setPrivacyClickListener() {
        binding.detailsButton.setOnClickListener {
            val message = SpannableStringBuilder()
                .bold { append("개인정보 수집 목적:\n") }
                .append("서비스 제공\n\n")
                .bold { append("수집 항목:\n") }
                .append(
                    "이름, 생년월일, 연락처, 성별,\n" +
                        "출발지 주소, 도착지 이름, 보호자 연락처,\n" +
                        "환자와의 관계, 예약 일자\n\n"
                )
                .bold { append("보유 및 이용 기간:\n") }
                .append("3년 후 파기\n\n")

            AlertDialog.Builder(requireContext())
                .setTitle("개인정보 수집 및 이용 동의")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show()
        }
    }

    private fun setThirdPrivacyClickListener() {
        binding.thirdDetailsButton.setOnClickListener {
            val message = SpannableStringBuilder()
                // 제공받는 자 (제목)
                .append("제공받는 자:\n")
                // 내용
                .bold { append("선택한 매니저\n\n") }

                // 제공받는 자의 이용 목적 (제목)
                .append("제공받는 자의 개인정보 이용 목적:\n")
                // 내용
                .bold { append("서비스 이용 및 관리\n\n") }

                // 제공하는 개인정보 항목 (제목)
                .append("제공하는 개인정보 항목:\n")
                // 내용
                .bold {
                    append(
                        "이름, 생년월일, 연락처, 주소,\n" +
                            "보호자 연락처, 주소, 환자와의 관계\n" +
                            "출발지 주소, 도착지 이름, 예약 일자\n\n"
                    )
                }

                // 보유 및 이용 기간 (제목)
                .append("제공 받는자의 보유기간:\n")
                // 내용
                .bold { append("3년 후 파기\n") }

            AlertDialog.Builder(requireContext())
                .setTitle("개인정보 제3자 제공 동의")
                .setMessage(message)
                .setPositiveButton("확인", null)
                .show()
        }
    }

    private fun setReservationClickListener() {
        binding.nextBtn.setOnClickListener {
            if (binding.privacyAgreementCheckbox.isChecked && binding.thirdPartyAgreementCheckbox.isChecked) {
                showConfirmationDialog()
            } else {
                Toast.makeText(requireContext(), "모든 항목에 동의해 주세요", Toast.LENGTH_SHORT).show()
            }
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
