package com.example.team25.domain.model

import android.os.Parcelable
import com.example.team25.domain.MedicineTime
import kotlinx.parcelize.Parcelize


@Parcelize
data class Report(
    val doctorSummary: String? = "",
    val frequency: Int? = 1,
    val medicineTime: MedicineTime? = null,
    val timeOfDays: String? = ""
): Parcelable
