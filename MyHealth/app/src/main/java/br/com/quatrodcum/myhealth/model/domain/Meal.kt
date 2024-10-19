package br.com.quatrodcum.myhealth.model.domain

data class Meal(
    val id: Int?,
    val name: String,
    val calories: Double,
    val description: String,
    val objective: Objective,
    val preparationMode: String,
    val ingredients: List<IngredientMeal>
)