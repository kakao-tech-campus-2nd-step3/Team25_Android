package com.example.team25.ui.main.status.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardInfor(
    private val cardNumber : String,
    private val expiredDate : String,
    private val password : String,
    private val birth : String
) : Parcelable{
    fun getCardNumber() = cardNumber
    fun getExpiredDate() = expiredDate
    fun getPassword() = password
    fun getBirth() = birth
}
