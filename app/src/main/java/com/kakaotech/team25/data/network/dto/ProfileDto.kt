package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: ProfileData?
)

data class ProfileData(
    @SerializedName("name") val name: String?,
    @SerializedName("profileImage") val profileImage: String?,
    @SerializedName("career") val career: String?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("workingRegion") val workingRegion: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("workingHour") val workingHour: WorkingHour?
)

data class WorkingHour(
    @SerializedName("monStartTime") val monStartTime: String?,
    @SerializedName("monEndTime") val monEndTime: String?,
    @SerializedName("tueStartTime") val tueStartTime: String?,
    @SerializedName("tueEndTime") val tueEndTime: String?,
    @SerializedName("wedStartTime") val wedStartTime: String?,
    @SerializedName("wedEndTime") val wedEndTime: String?,
    @SerializedName("thuStartTime") val thuStartTime: String?,
    @SerializedName("thuEndTime") val thuEndTime: String?,
    @SerializedName("friStartTime") val friStartTime: String?,
    @SerializedName("friEndTime") val friEndTime: String?,
    @SerializedName("satStartTime") val satStartTime: String?,
    @SerializedName("satEndTime") val satEndTime: String?,
    @SerializedName("sunStartTime") val sunStartTime: String?,
    @SerializedName("sunEndTime") val sunEndTime: String?
)
