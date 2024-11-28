package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.Meal

class MealDao() {

    fun get(mealId: Int): Meal? {
        val meals = db.meals.find("""{ "id" : $mealId }""")

        return meals.firstOrNull()
    }

    fun getAll(): List<Meal> {
        return db.meals.find()
    }

    fun getByObjective(objectiveId: Int): List<Meal> {
        return db.meals.find("""{ "objective" : $objectiveId }""")
    }

    fun insert(meal: Meal) {
        val name = meal.name
        val calories = meal.calories
        val description = meal.description
        val objective = meal.objective
        val preparationMode = meal.preparationMode

        val builder = StringBuilder()

        builder.append("{")
            .append("\"name\" : \"$name\", ")
            .append("\"calories\" : $calories, ")
            .append("\"description\" : \"$description\", ")
            .append("\"objective\" : ${objective.id}, ")
            .append("\"preparationMode\" : \"$preparationMode\", ")
            .append("\"ingredients\" : [ ")

        meal.ingredients.forEachIndexed { index, ing ->
            builder.append("{")
                .append("\"ingredient\" : ${ing.ingredient.id}, ")
                .append("\"unitOfMeasurement\" : ${ing.unitOfMeasurement.id}, ")
                .append("\"quantity\" : ${ing.quantity}")
                .append("}")

            if (index < meal.ingredients.size - 1) {
                builder.append(", ")
            }
        }

        builder.append("] ")
            .append("}")

        db.meals.insertOne(builder.toString())
    }


    fun update(meal: Meal) {
        val id = meal.id
        val name = meal.name
        val calories = meal.calories
        val description = meal.description
        val objective = meal.objective
        val preparationMode = meal.preparationMode

        val builder = StringBuilder()

        builder.append("{")
            .append("\"name\" : \"$name\", ")
            .append("\"calories\" : $calories, ")
            .append("\"description\" : \"$description\", ")
            .append("\"objective\" : ${objective.id}, ")
            .append("\"preparationMode\" : \"$preparationMode\", ")
            .append("\"ingredients\" : [ ")

        meal.ingredients.forEachIndexed { index, ing ->
            builder.append("{")
                .append("\"ingredient\" : ${ing.ingredient.id}, ")
                .append("\"unitOfMeasurement\" : ${ing.unitOfMeasurement.id}, ")
                .append("\"quantity\" : ${ing.quantity}")
                .append("}")

            if (index < meal.ingredients.size - 1) {
                builder.append(", ")
            }
        }

        builder.append("] ")
            .append("}")

        db.meals.updateMany(
            """ { "id" : $id }""",
            """{ "set" : $builder }"""
        )
    }

    fun countUsesByObjectiveId(id: Int): Int {
        return db.meals.find(
            """{ "objective" : $id }"""
        ).size
    }

    fun countUsesByIngredientId(id: Int): Int {
        return db.meals.find(
            """{ "ingredients.ingredient" : $id }"""
        ).size
    }

    fun countUsesByUnitOfMeasurement(id: Int): Int {
        return db.meals.find(
            """{ "ingredients.unitOfMeasurement" : $id }"""
        ).size
    }

    fun delete(id: Int) {
        db.meals.deleteMany(
            """{ "id" : $id }"""
        )
    }
}