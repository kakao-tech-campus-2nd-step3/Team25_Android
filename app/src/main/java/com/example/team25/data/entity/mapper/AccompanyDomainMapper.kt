package com.example.team25.data.entity.mapper

import com.example.team25.data.network.dto.AccompanyDto
import com.example.team25.domain.model.AccompanyInfo


object AccompanyDomainMapper {
    fun asDomainFromDto(dto: AccompanyDto): AccompanyInfo {
        return AccompanyInfo(
            status = dto.status,
            latitude = dto.latitude,
            longitude = dto.longitude,
            statusDate = dto.statusDate,
            statusDescribe = dto.statusDescribe
        )
    }
}

fun AccompanyDto.asDomainFromDto(): AccompanyInfo {
    return AccompanyDomainMapper.asDomainFromDto(this)
}
