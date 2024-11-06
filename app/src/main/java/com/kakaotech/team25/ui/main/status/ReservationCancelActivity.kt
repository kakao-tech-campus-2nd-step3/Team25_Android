package com.kakaotech.team25.ui.main.status

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakaotech.team25.R
import com.kakaotech.team25.databinding.ActivityReservationCancelBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationCancelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationCancelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationCancelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setReservationInfo()
        setCancelReasonDropDown()
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
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.managerNameTextView.text = it.managerName
            binding.reservationDateTextView.text = dateFormat.format(it.reservationDate)
        }
    }

    private fun setCancelReasonDropDown() {
        val cancelReasonOptions = resources.getStringArray(R.array.reservation_cancel_reason_option)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, cancelReasonOptions)
        binding.reservationCancelReasonAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.reservationCancelReasonAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val resCancelReason = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "선택된 값: $resCancelReason", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val KEY_RESERVATION_INFO = "ReservationInfo"
    }
}
