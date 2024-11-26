package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UserEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.ObjectiveEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.MealEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.UnitOfMeasurementEntity
import br.com.quatrodcum.myhealth.model.dao.mongodb.entity.IngredientMealEntity

data class Database(
    val users: MutableList<UserEntity>,
    val ingredients: MutableList<IngredientEntity>,
    val objectives: MutableList<ObjectiveEntity>,
    val meals: MutableList<MealEntity>,
    val unitOfMeasurements: MutableList<UnitOfMeasurementEntity>,
    val ingredientMeals: MutableList<IngredientMealEntity>
)