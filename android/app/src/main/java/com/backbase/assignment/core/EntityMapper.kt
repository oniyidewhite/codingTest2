package com.backbase.assignment.core

interface EntityMapper<EntityModel, DomainModel> {
    fun mapFromEntity(entity: EntityModel): DomainModel
}