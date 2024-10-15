package com.example.team25.data.entity.mapper

interface EntityMapper<Domain, Entity, Dto> {
    fun asEntity(domain: Domain): Entity

    fun asDomainFromEntity(entity: Entity): Domain

    fun asDomainFromDto(dto: Dto): Domain
}
