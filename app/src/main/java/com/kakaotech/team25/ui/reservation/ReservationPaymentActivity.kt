package com.kakaotech.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.databinding.ActivityReservationPaymentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReservationPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationPaymentBinding
    private val viewModel: ReservationPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()

        viewModel.checkCardStatus()
    }

    private fun setupObservers() {
        viewModel.cardStatusText.observe(this) { cardStatus ->
            binding.checkCardTextview.text = cardStatus
        }

        viewModel.billingKeyStatusMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message.contains("successful")) {
                binding.checkCardTextview.text = "카드가 존재하지 않습니다."
            } else if (message.contains("카드 정보가 존재하지 않습니다. 카드 등록 창으로 이동합니다.")) {
                navigateToAddCreditCardActivity()
            }
        }

        viewModel.paymentStatusMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message.contains("successful")) {
                finish()
            }
        }
    }

    private fun setupListeners() {
        binding.backBtn.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.creditView.setOnClickListener {
            viewModel.initiatePaymentCheck()
        }

        binding.checkCardTextview.setOnClickListener {
            viewModel.expireBillingKey(DeletePaymentRequest(orderId = ""))
        }
    }

    private fun navigateToAddCreditCardActivity() {
        val intent = Intent(this, AddCreditcardActivity::class.java)
        startActivity(intent)
    }
}
