package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo
import java.time.format.DateTimeFormatter

object ReservationMapper {
    fun asDomain(dto: List<ReservationDto>): List<ReservationInfo> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return dto.map { reservationDto ->
            ReservationInfo(
                managerId = reservationDto.managerId,
                reservationId = reservationDto.reservationId,
                managerName = "",
                reservationStatus = reservationDto.reservationStatus,
                departureLocation = reservationDto.departureLocation,
                arrivalLocation = reservationDto.arrivalLocation,
                reservationDateTime = reservationDto.reservationDate?.format(formatter),
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
