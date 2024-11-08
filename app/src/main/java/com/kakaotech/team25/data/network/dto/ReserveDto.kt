package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ReserveDto(
    @SerializedName("managerId") val managerId: Int,
    @SerializedName("departureLocation") val departureLocation: String,
    @SerializedName("arrivalLocation") val arrivalLocation: String,
    @SerializedName("reservationDateTime") val reservationDateTime: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("transportation") val transportation: String,
    @SerializedName("price") val price: Int,
    @SerializedName("patient") val patient: PatientDto
) {
    data class PatientDto(
        @SerializedName("name") val name: String,
        @SerializedName("phoneNumber") val phoneNumber: String,
        @SerializedName("patientGender") val patientGender: String,
        @SerializedName("patientRelation") val patientRelation: String,
        @SerializedName("birthDate") val birthDate: String,
        @SerializedName("nokPhone") val nokPhone: String
    )
}
