package com.example.team25.data.entity.mapper

import com.example.team25.data.entity.ReservationEntity
import com.example.team25.domain.model.Patient
import com.example.team25.domain.model.ReservationInfo

object ReservationEntityMapper : EntityMapper<List<ReservationInfo>, List<ReservationEntity>> {
    override fun asEntity(domain: List<ReservationInfo>): List<ReservationEntity> {
        return domain.map { reservationInfo ->
            ReservationEntity(
                managerId = reservationInfo.managerId,
                reservationStatus = reservationInfo.reservationStatus,
                departureLocation = reservationInfo.departureLocation,
                arrivalLocation = reservationInfo.arrivalLocation,
                reservationDate = reservationInfo.reservationDate,
                serviceType = reservationInfo.serviceType,
                transportation = reservationInfo.transportation,
                price = reservationInfo.price
            )
        }
    }

    override fun asDomain(entity: List<ReservationEntity>): List<ReservationInfo> {
        return entity.map { reservationEntity ->
            ReservationInfo(
                managerId = reservationEntity.managerId,
                managerName = "",
                reservationStatus = reservationEntity.reservationStatus,
                departureLocation = reservationEntity.departureLocation,
                arrivalLocation = reservationEntity.arrivalLocation,
                reservationDate = reservationEntity.reservationDate,
                serviceType = reservationEntity.serviceType,
                transportation = reservationEntity.transportation,
                price = reservationEntity.price,
                patient = Patient()
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
