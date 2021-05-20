package com.jvquiroz.mercadolibreproducts.data.domain.util

/**
 * Interface to define mapping for domain models
 */
interface ModelMapper <T, Model>{

    fun mapToModel(model: T): Model

    fun mapFromModel(domainModel: Model): T

    fun toDomainList(initial: List<T>): List<Model>{
        return initial.map { mapToModel(it) }
    }
}
