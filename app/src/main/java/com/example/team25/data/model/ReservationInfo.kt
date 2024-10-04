package com.example.team25.data.model

data class ReservationInfo(
    val userId: String,
    val managerId: String,
    val departure: String,
    val destination: String,
    val serviceDate: String,
    val serviceType: String,
    val transportation: String,
    val price: Int,
    val patient: Patient,
)
