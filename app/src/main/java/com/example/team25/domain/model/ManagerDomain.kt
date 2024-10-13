package com.example.team25.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ManagerDomain(
    val managerId: String = "",
    val name: String = "",
    val profileImage: String = "",
    val career: String = "",
    val comment: String = ""
) : Parcelable
