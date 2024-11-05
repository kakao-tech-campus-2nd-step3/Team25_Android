package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.team25.data.network.dto.BillingKeyDto
import com.example.team25.data.network.dto.DeletePaymentRequest
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
        viewModel.checkCardStatus()


        deleteCreditCard()
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
            Log.d("ReservationPayment", "Checking if billing key exists for payment...")
            viewModel.initiatePaymentCheck()
        }
    }
    private fun deleteCreditCard(){
        binding.checkCardTextview.setOnClickListener{
            Log.d("ReservationPayment", "Attempting to delete credit card...")
            deleteCard()
        }
    }
    private fun observeViewModel() {
        viewModel.billingKeyExpirationResponse.observe(this, Observer {response ->
            response?.let{
                if(it.status!!){
                    Log.d("ReservationPayment", "Billing key deletion successful: ${it.data?.resultMsg}")
                    Toast.makeText(this.getApplicationContext(),"삭제 완료", Toast.LENGTH_SHORT).show()
                    binding.checkCardTextview.text = "카드가 존재하지 않습니다."
                }else{
                    Log.e("ReservationPayment", "Billing key deletion failed: ${it.data?.resultMsg}")
                    Toast.makeText(this.getApplicationContext(),"${it.data!!.resultMsg}", Toast.LENGTH_SHORT).show()

                }
            }
        })
        viewModel.checkCardStatus.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                    if (it.data?.exists!!) {
                        Log.d("ReservationPayment", "Card exists: ${it.data.cardName}")
                        binding.checkCardTextview.text = "${it.data.cardName} (선택시 삭제)"
                    } else {
                        Log.d("ReservationPayment", "Card does not exist")
                        binding.checkCardTextview.text = "카드가 존재하지 않습니다."
                    }
                } else {
                    Log.e("ReservationPayment", "Failed to check card status")
                    Toast.makeText(this, "카드 상태 확인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.initiatePaymentStatus.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                    Log.d("ReservationPayment", "Billing key exists, initiating payment...")
                    // Billing Key가 존재할 경우 결제 요청
                    initiatePayment()
                } else {
                    Log.d("ReservationPayment", "Billing key does not exist, navigating to add credit card.")
                    navigateToAddCreditCardActivity()
                }
            }
        })


        viewModel.paymentResponse.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                    Log.d("ReservationPayment", "Payment successful: ${it.message}")
                    Toast.makeText(this.getApplicationContext(),"${it.message}", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.e("ReservationPayment", "Payment failed: ${it.message}")
                    Toast.makeText(this.getApplicationContext(), "${it.message}",Toast.LENGTH_SHORT).show()
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

    private fun deleteCard(){
        val id = DeletePaymentRequest(
            orderId = ""
        )
        viewModel.expireBillingKey(id)
    }


}
