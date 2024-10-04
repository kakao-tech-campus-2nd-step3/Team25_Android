package com.example.team25.data.entity.mapper

interface EntityMapper<Domain, Entity> {
    fun asEntity(domain: Domain): Entity

    fun asDomain(entity: Entity): Domain
}
