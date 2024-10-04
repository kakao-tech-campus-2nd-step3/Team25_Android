package com.example.team25.ui.main.status

import androidx.lifecycle.ViewModel
import com.example.team25.ui.main.status.data.DoctorCommentInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class ReservationCheckReportViewModel : ViewModel() {
    private val _doctorCommentInfo = MutableStateFlow<DoctorCommentInfo?>(null)
    val doctorCommentInfo: StateFlow<DoctorCommentInfo?> = _doctorCommentInfo

    init {
        setDoctorComment(
            """
            {
              "status": "200: OK",
              "message": "리포트 조회가 성공하였습니다.",
              "data": {
                "doctor_comment": "환자의 상태는 안정적입니다.",
                "medication_guidance": "매일 3회 식후 30분 복용"
              }
            }
            """.trimIndent(),
        )
    }

    fun setDoctorComment(jsonResponse: String) {
        val jsonObject = JSONObject(jsonResponse)
        val dataObject = jsonObject.getJSONObject("data")

        val doctorOpinion = dataObject.getString("doctor_comment")
        val medicationGuidance = dataObject.getString("medication_guidance")

        val guidanceParts = medicationGuidance.split(" ")

        var timeCycle: String? = null
        var mealTime: String? = null
        var time: String? = null

        if (guidanceParts.size >= 2) {
            timeCycle = "${guidanceParts[0]} ${guidanceParts[1]}"
        }

        for (i in 2 until guidanceParts.size) {
            when (guidanceParts[i]) {
                "식후" -> mealTime = "식후"
                "식전" -> mealTime = "식전"
                "30분" -> time = "30분"
                "1시간" -> time = "1시간"
            }
        }

        val doctorCommentInfo =
            DoctorCommentInfo(
                doctorOpinion = doctorOpinion,
                timeCycle = timeCycle,
                mealTime = mealTime,
                time = time,
            )

        _doctorCommentInfo.value = doctorCommentInfo
    }
}
