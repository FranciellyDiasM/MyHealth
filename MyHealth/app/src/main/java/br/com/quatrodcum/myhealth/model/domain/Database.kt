package br.com.quatrodcum.myhealth.model.domain

data class Database(
    val users: List<User>,
    val ingredients: List<Ingredient>,
    val objectives: List<Objective>,
    val meals: List<Meal>,
    val unitOfMeasurements: List<UnitOfMeasurement>,
    val ingredientMeals: List<IngredientMeal>
)