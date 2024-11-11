package com.kakaotech.team25.data.entity.mapper

import com.kakaotech.team25.data.entity.ReservationEntity
import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo

object ReservationEntityMapper : EntityMapper<List<ReservationInfo>, List<ReservationEntity>, List<ReservationDto>> {
    override fun asEntity(domain: List<ReservationInfo>): List<ReservationEntity> {
        return domain.map { reservationInfo ->
            ReservationEntity(
                managerId = reservationInfo.managerId,
                reservationId = reservationInfo.reservationId,
                reservationStatus = reservationInfo.reservationStatus,
                departureLocation = reservationInfo.departureLocation,
                arrivalLocation = reservationInfo.arrivalLocation,
                reservationDate = reservationInfo.reservationDateTime,
                serviceType = reservationInfo.serviceType,
                transportation = reservationInfo.transportation,
                price = reservationInfo.price
            )
        }
    }

    override fun asDomainFromEntity(entity: List<ReservationEntity>): List<ReservationInfo> {
        return entity.map { reservationEntity ->
            ReservationInfo(
                managerId = reservationEntity.managerId,
                managerName = "",
                reservationId = reservationEntity.reservationId,
                reservationStatus = reservationEntity.reservationStatus,
                departureLocation = reservationEntity.departureLocation,
                arrivalLocation = reservationEntity.arrivalLocation,
                reservationDateTime = reservationEntity.reservationDate,
                serviceType = reservationEntity.serviceType,
                transportation = reservationEntity.transportation,
                price = reservationEntity.price,
                patient = Patient()
            )
        }
    }

    override fun asDomainFromDto(dto: List<ReservationDto>): List<ReservationInfo> {
        return dto.map { reservationDto ->
            ReservationInfo(
                managerId = reservationDto.managerId,
                managerName = "",
                reservationStatus = reservationDto.reservationStatus,
                departureLocation = reservationDto.departureLocation,
                arrivalLocation = reservationDto.arrivalLocation,
                reservationDateTime = reservationDto.reservationDate,
                serviceType = reservationDto.serviceType,
                transportation = reservationDto.transportation,
                price = reservationDto.price,
                patient = Patient()
            )
        }
    }
}

fun List<ReservationInfo>.asEntity(): List<ReservationEntity> {
    return ReservationEntityMapper.asEntity(this)
}

fun List<ReservationEntity>?.asDomainFromEntity(): List<ReservationInfo> {
    return ReservationEntityMapper.asDomainFromEntity(this.orEmpty())
}

fun List<ReservationDto>?.asDomainFromDto(): List<ReservationInfo> {
    return ReservationEntityMapper.asDomainFromDto(this.orEmpty())
}
