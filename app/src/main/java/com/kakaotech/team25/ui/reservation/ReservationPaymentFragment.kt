package com.kakaotech.team25.ui.reservation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.databinding.FragmentReservationPaymentBinding
import com.kakaotech.team25.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationPaymentFragment : Fragment() {
    private var _binding: FragmentReservationPaymentBinding? = null
    private val binding get() = _binding!!

    private val reservationPaymentViewModel: ReservationPaymentViewModel by activityViewModels()
    private val reservationInfoViewModel: ReservationInfoViewModel by activityViewModels()

    private val addCardLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            reservationPaymentViewModel.updateBillingKeyStatus(BillingKeyStatus.EXIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReservationPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()

        reservationPaymentViewModel.checkCardStatus()
    }

    private fun setupObservers() {
        observeBillingKeyStatus()
        observePaymentStatus()
        observeReserveStatus()
        observeExpiredStatus()
        observeCancelStatus()
    }

    /**
     * 빌링키가 존재할 경우 예약 진행, 아니면 카드 등록 화면으로 전환
     */
    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("결제 확인")
        builder.setMessage("결제를 진행하시겠습니까?")
        builder.setPositiveButton("확인") { _, _ ->
            when (reservationPaymentViewModel.getBillingKeyStatus()) {
                BillingKeyStatus.EXIST -> {
                    reservationInfoViewModel.reserve()
                }
                BillingKeyStatus.NOT_EXIST -> {
                    Toast.makeText(requireContext(), "결제할 카드를 등록해주세요", Toast.LENGTH_SHORT).show()
                    navigateToAddCreditCardActivity()
                }

                BillingKeyStatus.FAILURE -> {
                    Toast.makeText(requireContext(), "서버 오류가 발생했습니다. 다음에 다시 예약해주세요", Toast.LENGTH_SHORT).show()
                }
                BillingKeyStatus.DEFAULT -> {
                    Toast.makeText(requireContext(), "등록된 카드 정보 조회중입니다. 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    /**
     * 예약에 성공하면 결제 진행, 실패하면 토스트 메시지 출력
     */
    private fun observeReserveStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationInfoViewModel.reserveStatus.collect { status ->
                    when (status) {
                        ReserveStatus.SUCCESS -> {
                            reservationPaymentViewModel.requestPayment()
                            reservationInfoViewModel.updateReserveStatus(ReserveStatus.DEFAULT)
                        }

                        ReserveStatus.FAILURE -> {
                            Toast.makeText(requireContext(), "예약 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            reservationInfoViewModel.updateReserveStatus(ReserveStatus.DEFAULT)
                        }

                        ReserveStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    /**
     * 결제에 성공하면 main 화면으로 이동, 실패하면 예약 취소 진행
     */
    private fun observePaymentStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationPaymentViewModel.paymentStatus.collect { status ->
                    when (status) {
                        PayStatus.SUCCESS -> {
                            Toast.makeText(requireContext(), "예약 완료", Toast.LENGTH_SHORT).show()
                            reservationPaymentViewModel.updatePaymentStatus(PayStatus.DEFAULT)
                            navigateToMainActivity()
                        }
                        PayStatus.FAILURE -> {
                            reservationPaymentViewModel.updatePaymentStatus(PayStatus.DEFAULT)
                            reservationInfoViewModel.cancelReservation(reservationInfoViewModel.getReservationId(), "단순변심", "결제 오류")
                        }
                        PayStatus.DEFAULT -> {}
                    }
                }
            }
        }
    }

    private fun observeCancelStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationInfoViewModel.cancelStatus.collect { status ->
                    when (status) {
                        ReserveStatus.SUCCESS -> {
                            Toast.makeText(requireContext(), "결제 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
                            reservationInfoViewModel.updateCancelStatus(ReserveStatus.DEFAULT)
                        }
                        ReserveStatus.FAILURE -> {
                            Toast.makeText(requireContext(), "오류가 발생하였습니다. 관리자에게 문의하세요", Toast.LENGTH_SHORT).show()
                            reservationInfoViewModel.updateCancelStatus(ReserveStatus.DEFAULT)
                        }
                        ReserveStatus.DEFAULT -> {

                        }
                    }
                }
            }
        }
    }

    private fun observeExpiredStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationPaymentViewModel.expireStatus.collect { status ->
                    when (status) {
                        PayStatus.SUCCESS -> {
                            Toast.makeText(requireContext(), "카드 삭제 성공", Toast.LENGTH_SHORT).show()
                            reservationPaymentViewModel.updateExpireStatus(PayStatus.DEFAULT)
                        }
                        PayStatus.FAILURE -> {
                            Toast.makeText(requireContext(), "카드 삭제 실패", Toast.LENGTH_SHORT).show()
                            reservationPaymentViewModel.updateExpireStatus(PayStatus.DEFAULT)
                        }
                        PayStatus.DEFAULT -> { }
                    }
                }
            }
        }
    }

    private fun observeBillingKeyStatus() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationPaymentViewModel.billingKeyStatus.collect { status ->
                    if (status == BillingKeyStatus.EXIST) {
                        binding.removeCardTextView.visibility = View.VISIBLE
                    } else {
                        binding.removeCardTextView.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.creditView.setOnClickListener {
            showConfirmationDialog()
        }

        binding.removeCardTextView.setOnClickListener {
            reservationPaymentViewModel.expireBillingKey(DeletePaymentRequest(orderId = ""))
        }
    }

    private fun navigateToAddCreditCardActivity() {
        val intent = Intent(requireContext(), AddCreditCardActivity::class.java)
        addCardLauncher.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
