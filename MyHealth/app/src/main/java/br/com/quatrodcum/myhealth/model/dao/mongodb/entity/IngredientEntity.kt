package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

import br.com.quatrodcum.myhealth.model.domain.Ingredient

data class IngredientEntity(
    var id: Int? = null,
    var name: String?
) {
    companion object {
        fun IngredientEntity.toDomain(): Ingredient {
            return Ingredient(
                id = this.id ?: -1,
                name = this.name.orEmpty()
            )
        }
    }
}