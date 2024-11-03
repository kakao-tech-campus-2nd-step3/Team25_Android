package com.example.team25.data.entity.mapper

import com.example.team25.data.network.dto.AccompanyDto
import com.example.team25.domain.model.AccompanyInfo
import java.time.LocalDateTime


object AccompanyDomainMapper {
    fun asDomainFromDto(dto: List<AccompanyDto>): List<AccompanyInfo> {
        return dto.map {
            AccompanyInfo(
                status = it.status,
                statusDate = LocalDateTime.parse(it.statusDate),
                statusDescribe = it.statusDescribe
            )
        }.sortedBy { it.statusDate }
    }
}

fun List<AccompanyDto>?.asDomainFromDto(): List<AccompanyInfo> {
    return AccompanyDomainMapper.asDomainFromDto(this.orEmpty())
}
