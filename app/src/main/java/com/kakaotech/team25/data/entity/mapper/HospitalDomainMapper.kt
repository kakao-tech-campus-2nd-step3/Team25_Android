package com.kakaotech.team25.data.entity.mapper

import com.kakaotech.team25.data.network.dto.HospitalDto
import com.kakaotech.team25.domain.model.HospitalDomain

object HospitalDomainMapper {

    fun asDomainFromDto(dto: List<HospitalDto>): List<HospitalDomain> {
        return dto.map { hospitalDto ->
            HospitalDomain(
                placeId = hospitalDto.id,
                name = hospitalDto.name,
                address = hospitalDto.address,
            )
        }
    }
}

fun List<HospitalDto>?.asDomainFromDto(): List<HospitalDomain> {
    return HospitalDomainMapper.asDomainFromDto(this.orEmpty())
}
