package com.example.team25.ui.main.status.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

class ReservationInfo {
    @Parcelize
    data class ReservationInfo(
        val id: String,
        val name: String,
        val date: Date,
    ) : Parcelable
}
