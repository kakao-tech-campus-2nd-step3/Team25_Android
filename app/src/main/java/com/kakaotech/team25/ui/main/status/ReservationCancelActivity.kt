package com.kakaotech.team25.ui.main.status

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.ActivityReservationCancelBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

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
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
            val outputFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)
            val dateString = it.reservationDateTime
            val date = try {
                dateString?.let { inputFormat.parse(it) }
            } catch (e: ParseException) {
                null
            }

            val formattedDate = date?.let { outputFormat.format(it) } ?: "날짜 없음"

            binding.managerNameTextView.text = it.managerName
            binding.reservationDateTextView.text = formattedDate

            reservationCancelViewModel.updateReservationId(it.reservationId)
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
            reservationCancelViewModel.cancelReservation()
        }
    }

    companion object {
        const val KEY_RESERVATION_INFO = "ReservationInfo"
    }
}
