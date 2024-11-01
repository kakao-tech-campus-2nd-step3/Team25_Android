package com.example.team25.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccompanyInfo(
    val status: String,
    val latitude: Double,
    val longitude: Double,
    val statusDate: String,
    val statusDescribe: String
): Parcelable
