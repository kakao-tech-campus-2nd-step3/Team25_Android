package com.example.team25.entity.mapper

interface EntityMapper<Domain, Entity> {

    fun asEntity(domain: Domain): Entity

    fun asDomain(entity: Entity): Domain
}

