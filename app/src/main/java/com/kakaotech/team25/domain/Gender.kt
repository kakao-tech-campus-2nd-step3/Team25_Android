package com.kakaotech.team25.domain

enum class Gender {
    MALE,
    FEMALE,
    UNKNOWN
}

fun Gender.toKorean(): String {
    return when (this) {
        Gender.MALE -> "남성"
        Gender.FEMALE -> "여성"
        Gender.UNKNOWN -> ""
    }
}
