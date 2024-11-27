package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement


data class UnitOfMeasurementEntity(
    var id: Int? = null,
    var name: String?
) {
    companion object {
        fun UnitOfMeasurementEntity.toDomain(): UnitOfMeasurement {
            return UnitOfMeasurement(
                id = this.id ?: -1,
                name = this.name.orEmpty()
            )
        }
    }
}