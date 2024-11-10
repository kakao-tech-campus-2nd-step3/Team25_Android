package com.kakaotech.team25.ui.main.status

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakaotech.team25.R
import com.kakaotech.team25.data.util.DateFormatter
import com.kakaotech.team25.databinding.ActivityReservationCancelBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReservationCancelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationCancelBinding
    private val reservationCancelViewModel: ReservationCancelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationCancelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReservationInfo()
        setCancelReasonDropDown()
        setCancelDetailsListener()
        setCancelBtnClickListener()
        collectToastMessage()
        navigateToPrevious()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra(KEY_RESERVATION_INFO)
        reservationInfo?.let {
            binding.managerNameTextView.text = it.managerName
            binding.reservationDateTextView.text = DateFormatter.formatDate(it.reservationDateTime)

            reservationCancelViewModel.updateReservationId(it.reservationId)
        }
    }

    private fun collectToastMessage() {
        lifecycleScope.launch {
            reservationCancelViewModel.toastMessage.collectLatest { message ->
                if (!message.isNullOrEmpty())
                    Toast.makeText(this@ReservationCancelActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCancelReasonDropDown() {
        val cancelReasonOptions = resources.getStringArray(R.array.reservation_cancel_reason_option)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, cancelReasonOptions)
        binding.reservationCancelReasonAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.reservationCancelReasonAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val resCancelReason = parent.getItemAtPosition(position).toString()
            reservationCancelViewModel.updateCancelReason(resCancelReason)
        }
    }

    private fun setCancelDetailsListener() {
        binding.cancelDetailsEditTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                reservationCancelViewModel.updateCancelDetails(s.toString())
            }
        })
    }

    private fun setCancelBtnClickListener() {
        binding.cancelReservationBtn.setOnClickListener {
            val cancelReason = binding.reservationCancelReasonAutoCompleteTextView.text.toString()
            if (cancelReason.isBlank()) {
                Toast.makeText(this, "취소 사유를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            reservationCancelViewModel.cancelReservation()
            finish()
        }
    }

    companion object {
        const val KEY_RESERVATION_INFO = "ReservationInfo"
    }
}
