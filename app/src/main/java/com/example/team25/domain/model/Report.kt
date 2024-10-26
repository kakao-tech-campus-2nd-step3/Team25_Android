package com.example.team25.domain.model

import android.os.Parcelable
import com.example.team25.domain.MedicinTime
import kotlinx.parcelize.Parcelize


@Parcelize
data class Report(
    val doctorSummary: String,
    val frequency: String,
    val medicinTime: MedicinTime,
    val timeOfDays: String
): Parcelable
