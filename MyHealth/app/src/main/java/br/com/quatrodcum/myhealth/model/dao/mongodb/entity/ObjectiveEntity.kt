package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

import br.com.quatrodcum.myhealth.model.domain.Objective

data class ObjectiveEntity(
    var id: Int? = null,
    var description: String?
) {
    companion object {
        fun ObjectiveEntity.toDomain(): Objective {
            return Objective(
                id = this.id ?: -1,
                description = this.description.orEmpty()
            )
        }

        fun List<ObjectiveEntity>.toDomain(): List<Objective> {
            return this.map {
                Objective(
                    id = it.id ?: -1,
                    description = it.description.orEmpty()
                )
            }
        }
    }
}