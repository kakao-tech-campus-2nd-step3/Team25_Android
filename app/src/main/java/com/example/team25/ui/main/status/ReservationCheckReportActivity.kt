package com.example.team25.ui.main.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import com.example.team25.databinding.ActivityReservationCheckReportBinding
import com.example.team25.ui.main.status.data.ReservationInfo
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationCheckReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationCheckReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationCheckReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setWriterInfo()
        setDoctorComment(mockJsonResponse())
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

            binding.managerNameTextView.text = it.name
            binding.dateTextView.text = dateFormat.format(it.date)
        }
    }

    private fun mockJsonResponse(): String {
        return """
            {
              "status": "200: OK",
              "message": "리포트 조회가 성공하였습니다.",
              "data": {
                "doctor_comment": "환자의 상태는 안정적입니다.",
                "medication_guidance": "매일 3회 식후 30분 복용"
              }
            }
            """.trimIndent()
    }

    private fun setDoctorComment(jsonResponse: String) {
        val jsonObject = JSONObject(jsonResponse)
        val dataObject = jsonObject.getJSONObject("data")

        val doctorOpinion = dataObject.getString("doctor_comment")
        val medicationGuidance = dataObject.getString("medication_guidance")

        val guidanceParts = medicationGuidance.split(" ")

        binding.doctorOpinionTextView.text = doctorOpinion

        if (guidanceParts.size >= 2) {
            binding.timeCycleBtn.text = guidanceParts[0] + " " + guidanceParts[1]
            binding.timeCycleBtn.setBackgroundResource(R.drawable.purple_btn_box)
        }

        for (i in 2 until guidanceParts.size) {
            when (guidanceParts[i]) {
                "식후" -> {
                    binding.mealAfterBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
                "식전" -> {
                    binding.mealBeforeBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
                "30분" -> {
                    binding.time30minBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
                "1시간" -> {
                    binding.time1hourBtn.setBackgroundResource(R.drawable.purple_btn_box)
                }
            }
        }
    }
}
