package com.kakaotech.team25.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AccompanyInfo(
    val status: String = "",
    val statusDate: LocalDateTime = LocalDateTime.now(),
    val statusDescribe: String = ""
): Parcelable
