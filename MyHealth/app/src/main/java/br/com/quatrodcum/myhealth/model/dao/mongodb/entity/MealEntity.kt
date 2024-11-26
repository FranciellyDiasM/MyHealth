package br.com.quatrodcum.myhealth.model.dao.mongodb.entity

data class MealEntity(
    var id: Int?,
    var name: String,
    var calories: Double,
    var description: String,
    var objective: Int,
    var preparationMode: String,
    var ingredients: List<IngredientMealEntity>
)