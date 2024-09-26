package com.example.team25.data

data class ReservationInfo(
    val user_id: String,
    val manager_id: String,
    val departure: String,
    val destination: String,
    val service_date: String,
    val service_type: String,
    val transportation: String,
    val price: Int,
    val patient: Patient
)
