package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective

data class MealEntity(
    var id: Int? = null,
    var name: String?,
    var calories: Double?,
    var description: String?,
    var objective: Int?,
    var preparationMode: String?,
    var ingredients: MutableList<IngredientMealEntity>?
) {
    companion object {
        fun MealEntity.toDomain(objective: Objective, ingredients: List<IngredientMeal>): Meal {
            return Meal(
                id = this.id ?: -1,
                name = this.name.orEmpty(),
                calories = this.calories ?: -1.0,
                description = this.description.orEmpty(),
                objective = objective,
                preparationMode = this.preparationMode.orEmpty(),
                ingredients = ingredients,
            )
        }
    }
}