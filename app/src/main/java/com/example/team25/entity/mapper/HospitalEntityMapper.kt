package com.example.team25.entity.mapper

import com.example.team25.domain.HospitalDomain
import com.example.team25.entity.HospitalEntity

object HospitalEntityMapper : EntityMapper<List<HospitalDomain>, List<HospitalEntity>> {

    override fun asEntity(domain: List<HospitalDomain>): List<HospitalEntity> {
        return domain.map { hospital ->
            HospitalEntity(
                placeId = hospital.placeId,
                name = hospital.name,
                address = hospital.address
            )
        }
    }

    override fun asDomain(entity: List<HospitalEntity>): List<HospitalDomain> {
        return entity.map { hospitalEntity ->
            HospitalDomain(
                placeId = hospitalEntity.placeId,
                name = hospitalEntity.name,
                address = hospitalEntity.address
            )
        }
    }
}

fun List<HospitalDomain>.asEntity(): List<HospitalEntity> {
    return HospitalEntityMapper.asEntity(this)
}

fun List<HospitalEntity>?.asDomain(): List<HospitalDomain> {
    return HospitalEntityMapper.asDomain(this.orEmpty())
}
