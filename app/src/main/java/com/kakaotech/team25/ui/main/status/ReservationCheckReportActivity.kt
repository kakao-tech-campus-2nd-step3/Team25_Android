package com.kakaotech.team25.ui.main.status

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kakaotech.team25.R
import com.kakaotech.team25.data.util.DateFormatter
import com.kakaotech.team25.databinding.ActivityReservationCheckReportBinding
import com.kakaotech.team25.domain.MedicineTime
import com.kakaotech.team25.domain.model.Report
import com.kakaotech.team25.domain.model.ReservationInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ReservationCheckReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationCheckReportBinding
    private val viewModel: ReservationCheckReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationCheckReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        updateReservationInfo()
        collectReservationInfo()
        collectReportInfo()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun updateReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("ReservationInfo")
        reservationInfo?.let { reservation ->
            viewModel.updateReservationInfo(reservation)
        }
    }

    private fun collectReservationInfo() {
        lifecycleScope.launch {
            viewModel.reservationInfo.collectLatest { reservation ->
                binding.managerNameTextView.text = reservation.managerName
                binding.dateTextView.text = DateFormatter.formatDate(
                    reservation.reservationDateTime,
                    outputFormat = SimpleDateFormat("yy.MM.dd", Locale.KOREAN)
                )
            }
        }
    }

    private fun collectReportInfo() {
        lifecycleScope.launch {
            viewModel.reportInfo.collectLatest { reportInfo ->
                updateReportUI(reportInfo)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateReportUI(reportInfo: Report) {
        reportInfo.doctorSummary.let { doctorOpinion ->
            binding.doctorOpinionTextView.text = doctorOpinion
        }

        reportInfo.frequency.let { frequency ->
            binding.timeCycleBtn.text = "매일 ${frequency}회"
            binding.timeCycleBtn.setBackgroundResource(R.drawable.purple_btn_box)
        }

        reportInfo.medicineTime.let { medicineTime ->
            when (medicineTime) {
                MedicineTime.AFTER_MEAL -> {
                    binding.mealAfterBtn.setBackgroundResource(R.drawable.purple_btn_box)
                    binding.time30minBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
                MedicineTime.BEFORE_MEAL -> {
                    binding.mealBeforeBtn.setBackgroundResource(R.drawable.purple_btn_box)
                    binding.time30minBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
                null -> {}
            }
        }

        reportInfo.timeOfDays.let { timeOfDays ->
            val times = timeOfDays?.split(" ")
            times?.forEach { time ->
                when (time) {
                    "아침" -> binding.morningBtn.setBackgroundResource(R.drawable.purple_btn_box)
                    "점심" -> binding.lunchBtn.setBackgroundResource(R.drawable.purple_btn_box)
                    "저녁" -> binding.dinnerBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
            }
        }
    }
}
