package com.example.team25.ui.main.status

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationCheckReportBinding
import com.example.team25.domain.MedicineTime
import com.example.team25.domain.model.Report
import com.example.team25.domain.model.ReservationInfo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
                val dateFormat = SimpleDateFormat("yy.MM.dd", Locale.KOREAN)

                binding.managerNameTextView.text = reservation.managerName
                binding.dateTextView.text = dateFormat.format(reservation.reservationDate)
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
                MedicineTime.AFTER_MEAL -> binding.mealAfterBtn.setBackgroundResource(R.drawable.purple_btn_box)
                MedicineTime.BEFORE_MEAL -> binding.mealBeforeBtn.setBackgroundResource(R.drawable.purple_btn_box)
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

        /* api 멤버 추가 예정
        reportInfo.let { 시간 ->
            when (시간) {
                "30분" -> binding.time30minBtn.setBackgroundResource(R.drawable.purple_btn_box)
                "1시간" -> binding.time1hourBtn.setBackgroundResource(R.drawable.purple_btn_box)
            }
        }*/
    }
}
