package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.team25.data.network.dto.BillingKeyDto
import com.example.team25.databinding.ActivityReservationPaymentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReservationPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationPaymentBinding
    private val viewModel: ReservationPaymentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        clickCreditCard()
        navigateToPrevious()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun clickCreditCard() {
        binding.creditView.setOnClickListener {
            viewModel.checkBillingKeyExists()
        }
    }
    private fun observeViewModel() {
        viewModel.billingKeyExistsResponse.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                    // Billing Key가 존재할 경우 결제 요청
                    initiatePayment()
                } else {
                    navigateToAddCreditCardActivity()
                }
            }
        })
        viewModel.paymentResponse.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                  // 결제 성공
                } else {
                   // 결제 실패
                }
            }
        })
        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {

            }
        })
    }
    private fun navigateToAddCreditCardActivity() {
        val intent = Intent(this, AddCreditcardActivity::class.java)
        startActivity(intent)
    }

    private fun initiatePayment() {
        val paymentRequest = BillingKeyDto(
            amount = 1000,
            goodsName = "테스트 상품",
            cardQuota = "0",
            useShopInterest = false
        )
        viewModel.requestPayment(paymentRequest)
    }

}
