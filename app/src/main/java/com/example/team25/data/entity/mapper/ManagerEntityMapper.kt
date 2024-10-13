package com.example.team25.data.entity.mapper

import com.example.team25.data.entity.ManagerEntity
import com.example.team25.domain.model.ManagerDomain


object ManagerEntityMapper: EntityMapper<List<ManagerDomain>, List<ManagerEntity>> {
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

    override fun asDomain(entity: List<ManagerEntity>): List<ManagerDomain> {
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
}

fun List<ManagerDomain>.asEntity(): List<ManagerEntity> {
    return ManagerEntityMapper.asEntity(this)
}

fun List<ManagerEntity>?.asDomain(): List<ManagerDomain> {
    return ManagerEntityMapper.asDomain(this.orEmpty())
}
