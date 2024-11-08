package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo

object ReservationMapper {
    fun asDomain(dto: List<ReservationDto>): List<ReservationInfo> {
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

fun List<ReservationDto>?.asDomain(): List<ReservationInfo> {
    return ReservationMapper.asDomain(this.orEmpty())
}
