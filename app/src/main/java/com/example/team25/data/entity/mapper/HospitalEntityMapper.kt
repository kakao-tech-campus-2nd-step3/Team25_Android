package com.example.team25.data.entity.mapper

import com.example.team25.data.entity.HospitalEntity
import com.example.team25.data.network.dto.HospitalDto
import com.example.team25.domain.model.HospitalDomain

object HospitalEntityMapper : EntityMapper<List<HospitalDomain>, List<HospitalEntity>, List<HospitalDto>> {
    override fun asEntity(domain: List<HospitalDomain>): List<HospitalEntity> {
        return domain.map { hospital ->
            HospitalEntity(
                placeId = hospital.placeId,
                name = hospital.name,
                address = hospital.address,
            )
        }
    }

    override fun asDomainFromEntity(entity: List<HospitalEntity>): List<HospitalDomain> {
        return entity.map { hospitalEntity ->
            HospitalDomain(
                placeId = hospitalEntity.placeId,
                name = hospitalEntity.name,
                address = hospitalEntity.address,
            )
        }
    }

    override fun asDomainFromDto(dto: List<HospitalDto>): List<HospitalDomain> {
        return dto.map { hospitalDto ->
            HospitalDomain(
                placeId = hospitalDto.id,
                name = hospitalDto.name,
                address = hospitalDto.address,
            )
        }
    }
}

fun List<HospitalDomain>.asEntity(): List<HospitalEntity> {
    return HospitalEntityMapper.asEntity(this)
}

fun List<HospitalEntity>?.asDomainFromEntity(): List<HospitalDomain> {
    return HospitalEntityMapper.asDomainFromEntity(this.orEmpty())
}

fun List<HospitalDto>?.asDomainFromDto(): List<HospitalDomain> {
    return HospitalEntityMapper.asDomainFromDto(this.orEmpty())
}
