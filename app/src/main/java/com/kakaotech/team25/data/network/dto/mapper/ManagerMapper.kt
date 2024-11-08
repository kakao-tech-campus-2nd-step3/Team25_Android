package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.ManagerDto
import com.kakaotech.team25.domain.model.ManagerDomain


object ManagerMapper: DomainMapper<List<ManagerDomain>, List<ManagerDto>> {
    override fun asDomain(dto: List<ManagerDto>): List<ManagerDomain> {
        return dto.map { managerDto ->
            ManagerDomain(
                managerId = managerDto.managerId,
                name = managerDto.name,
                profileImage = managerDto.profileImage,
                career = managerDto.career,
                comment = managerDto.comment
            )
        }
    }

    override fun asDto(domain: List<ManagerDomain>): List<ManagerDto> {
        return domain.map { managerDomain ->
            ManagerDto(
                managerId = managerDomain.managerId,
                name = managerDomain.name,
                profileImage = managerDomain.profileImage,
                career = managerDomain.career,
                comment = managerDomain.comment
            )
        }
    }
}

fun List<ManagerDto>?.asDomain(): List<ManagerDomain> {
    return ManagerMapper.asDomain(this.orEmpty())
}

fun List<ManagerDomain>?.asDto(): List<ManagerDto> {
    return ManagerMapper.asDto(this.orEmpty())
}
