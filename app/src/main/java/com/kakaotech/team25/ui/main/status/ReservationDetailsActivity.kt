package com.kakaotech.team25.ui.main.status

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakaotech.team25.data.util.DateFormatter
import com.kakaotech.team25.databinding.ActivityReservationDetailsBinding
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.toKorean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    private val reservationDetailsViewModel: ReservationDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationInfo()
        collectReservationInfo()

    }

    private fun setReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("ReservationInfo")
        reservationInfo?.let {
            reservationDetailsViewModel.updateReservationInfo(reservationInfo)
        }
    }

    private fun collectReservationInfo() {
        lifecycleScope.launch {
            reservationDetailsViewModel.reservationInfo.collectLatest {reservationInfo ->
                binding.locationDepartTextView.text = reservationInfo?.departureLocation
                binding.locationArriveTextView.text = reservationInfo?.arrivalLocation
                binding.companionDepartTimeInformationTextView.text = DateFormatter.formatDate(reservationInfo?.reservationDateTime, outputFormat = SimpleDateFormat("yy.MM.dd a h시", Locale.KOREAN))
                binding.transportationInformationTextView.text = reservationInfo?.transportation
                binding.requestDetailsInformationTextView.text = "없음"

                binding.userNameInformationTextView.text = reservationInfo?.patient?.patientName
                binding.userGenderInformationTextView.text = reservationInfo?.patient?.patientGender?.toKorean()
                binding.userBirthInformationTextView.text = reservationInfo?.patient?.patientBirth
                binding.userPhoneNumberInformationTextView.text = reservationInfo?.patient?.patientPhone
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
