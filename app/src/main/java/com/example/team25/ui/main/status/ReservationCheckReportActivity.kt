package com.example.team25.ui.main.status

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationCheckReportBinding
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.ui.main.status.data.DoctorCommentInfo
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
        setWriterInfo()
        collectDoctorComment()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setWriterInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("ReservationInfo")
        reservationInfo?.let {
            val dateFormat = SimpleDateFormat("yy.MM.dd", Locale.KOREAN)

            binding.managerNameTextView.text = it.managerName
            binding.dateTextView.text = dateFormat.format(it.reservationDate)
        }
    }

    private fun collectDoctorComment() {
        lifecycleScope.launch {
            viewModel.doctorCommentInfo.collectLatest { data ->
                data?.let {
                    updateReportUI(it)
                }
            }
        }
    }

    private fun updateReportUI(info: DoctorCommentInfo) {
        binding.doctorOpinionTextView.text = info.doctorOpinion

        info.timeCycle?.let {
            binding.timeCycleBtn.text = it
            binding.timeCycleBtn.setBackgroundResource(R.drawable.purple_btn_box)
        }

        info.mealTime?.let {
            when (it) {
                "식후" -> binding.mealAfterBtn.setBackgroundResource(R.drawable.purple_btn_box)
                "식전" -> binding.mealBeforeBtn.setBackgroundResource(R.drawable.purple_btn_box)
            }
        }

        info.time?.let {
            when (it) {
                "30분" -> binding.time30minBtn.setBackgroundResource(R.drawable.purple_btn_box)
                "1시간" -> binding.time1hourBtn.setBackgroundResource(R.drawable.purple_btn_box)
            }
        }
    }
}
