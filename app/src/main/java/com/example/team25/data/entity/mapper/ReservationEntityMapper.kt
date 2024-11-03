package com.example.team25.data.entity.mapper

import com.example.team25.data.entity.ReservationEntity
import com.example.team25.data.network.dto.ReservationDto
import com.example.team25.domain.ReservationStatus
import com.example.team25.domain.model.Patient
import com.example.team25.domain.model.ReservationInfo

object ReservationEntityMapper : EntityMapper<List<ReservationInfo>, List<ReservationEntity>, List<ReservationDto>> {
    override fun asEntity(domain: List<ReservationInfo>): List<ReservationEntity> {
        return domain.map { reservationInfo ->
            ReservationEntity(
                managerId = reservationInfo.managerId,
                reservationId = reservationInfo.reservationId,
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

    override fun asDomainFromEntity(entity: List<ReservationEntity>): List<ReservationInfo> {
        return entity.map { reservationEntity ->
            ReservationInfo(
                managerId = reservationEntity.managerId,
                managerName = "",
                reservationId = reservationEntity.reservationId,
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

    override fun asDomainFromDto(dto: List<ReservationDto>): List<ReservationInfo> {
        return dto.map { reservationDto ->
            ReservationInfo(
                managerId = reservationDto.managerId,
                managerName = "",
                reservationStatus = when (reservationDto.reservationStatus) {
                    "동행 중" -> ReservationStatus.RUNNING
                    "보류" -> ReservationStatus.PEND
                    "확정" -> ReservationStatus.CONFIRM
                    "취소" -> ReservationStatus.CANCEL
                    else -> ReservationStatus.PEND
                },
                departureLocation = reservationDto.departureLocation,
                arrivalLocation = reservationDto.arrivalLocation,
                reservationDate = reservationDto.reservationDate,
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
