package br.com.quatrodcum.myhealth.model.domain

data class IngredientMeal(
    val ingredient: Ingredient,
    val unitOfMeasurement: UnitOfMeasurement,
    val quantity: Int
)
