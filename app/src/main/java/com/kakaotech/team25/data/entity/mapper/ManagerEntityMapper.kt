package com.kakaotech.team25.data.entity.mapper

import com.kakaotech.team25.data.entity.ManagerEntity
import com.kakaotech.team25.data.network.dto.ManagerDto
import com.kakaotech.team25.domain.model.ManagerDomain


object ManagerEntityMapper: EntityMapper<List<ManagerDomain>, List<ManagerEntity>, List<ManagerDto>> {
    override fun asEntity(domain: List<ManagerDomain>): List<ManagerEntity> {
        return domain.map { manager ->
            ManagerEntity(
                managerId = manager.managerId,
                name = manager.name,
                profileImange = manager.profileImage,
                career = manager.career,
                comment = manager.comment
            )
        }
    }

    override fun asDomainFromEntity(entity: List<ManagerEntity>): List<ManagerDomain> {
        return entity.map { managerEntity ->
            ManagerDomain(
                managerId = managerEntity.managerId,
                name = managerEntity.name,
                profileImage = managerEntity.profileImange,
                career = managerEntity.career,
                comment = managerEntity.comment
            )
        }
    }

    override fun asDomainFromDto(dto: List<ManagerDto>): List<ManagerDomain> {
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
}

fun List<ManagerDomain>.asEntity(): List<ManagerEntity> {
    return ManagerEntityMapper.asEntity(this)
}

fun List<ManagerEntity>?.asDomainFromEntity(): List<ManagerDomain> {
    return ManagerEntityMapper.asDomainFromEntity(this.orEmpty())
}

fun List<ManagerDto>?.asDomainFromDto(): List<ManagerDomain> {
    return ManagerEntityMapper.asDomainFromDto(this.orEmpty())
}
