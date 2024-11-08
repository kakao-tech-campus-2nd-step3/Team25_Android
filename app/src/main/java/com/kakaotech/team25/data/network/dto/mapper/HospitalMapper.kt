package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.HospitalDto
import com.kakaotech.team25.domain.model.HospitalDomain

object HospitalDomainMapper {

    fun asDomain(dto: List<HospitalDto>): List<HospitalDomain> {
        return dto.map { hospitalDto ->
            HospitalDomain(
                placeId = hospitalDto.id,
                name = hospitalDto.name,
                address = hospitalDto.address,
            )
        }
    }
}

fun List<HospitalDto>?.asDomain(): List<HospitalDomain> {
    return HospitalDomainMapper.asDomain(this.orEmpty())
}
