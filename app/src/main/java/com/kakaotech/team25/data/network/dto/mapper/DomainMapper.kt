package com.kakaotech.team25.data.network.dto.mapper

interface DomainMapper<Domain,Dto> {

    fun asDomain(dto: Dto): Domain

    fun asDto(domain: Domain): Dto
}
