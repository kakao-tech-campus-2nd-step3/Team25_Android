package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.AccompanyDto
import com.kakaotech.team25.domain.model.AccompanyInfo
import java.time.LocalDateTime

object AccompanyMapper {
    fun asDomain(dto: List<AccompanyDto>): List<AccompanyInfo> {
        return dto.map {
            AccompanyInfo(
                status = it.status,
                statusDate = LocalDateTime.parse(it.statusDate),
                statusDescribe = it.statusDescribe
            )
        }.sortedBy { it.statusDate }
    }
}

fun List<AccompanyDto>?.asDomain(): List<AccompanyInfo> {
    return AccompanyMapper.asDomain(this.orEmpty())
}
