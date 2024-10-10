package com.example.team25.data.entity.mapper

import com.example.team25.data.entity.ReservationEntity
import com.example.team25.domain.model.ReservationInfo

object ReservationEntityMapper: EntityMapper<List<ReservationInfo>, List<ReservationEntity>> {
    override fun asEntity(domain: List<ReservationInfo>): List<ReservationEntity> {
        return domain.map { reservationInfo ->
            ReservationEntity(
                managerId = reservationInfo.managerId,
                managerName = reservationInfo.managerName,
                reservationStatus = reservationInfo.reservationStatus,
                departure = reservationInfo.departure,
                destination = reservationInfo.destination,
                serviceDate = reservationInfo.serviceDate,
                serviceType = reservationInfo.serviceType,
                transportation = reservationInfo.transportation,
                price = reservationInfo.price,
                patient = reservationInfo.patient
            )
        }
    }

    override fun asDomain(entity: List<ReservationEntity>): List<ReservationInfo> {
        return entity.map { reservationEntity ->
            ReservationInfo(
                managerId = reservationEntity.managerId,
                managerName = reservationEntity.managerName,
                reservationStatus = reservationEntity.reservationStatus,
                departure = reservationEntity.departure,
                destination = reservationEntity.destination,
                serviceDate = reservationEntity.serviceDate,
                serviceType = reservationEntity.serviceType,
                transportation = reservationEntity.transportation,
                price = reservationEntity.price,
                patient = reservationEntity.patient
            )
        }
    }
}

fun List<ReservationInfo>.asEntity(): List<ReservationEntity> {
    return ReservationEntityMapper.asEntity(this)
}

fun List<ReservationEntity>?.asDomain(): List<ReservationInfo> {
    return ReservationEntityMapper.asDomain(this.orEmpty())
}
