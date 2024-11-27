package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.Ingredient

class IngredientDao() {

    fun getAll(): List<Ingredient> {
        return db.ingredients.find()
    }

    fun insert(ingredient: Ingredient) {
        val name = ingredient.name

        db.ingredients.insertOne("""
            { "name" : "$name" }
        """)
    }

    fun update(ingredient: Ingredient) {
        val id = ingredient.id
        val name = ingredient.name

        db.ingredients.updateMany(
            """{ "id" : $id }""",
            """{ "set" : { "name" : "$name" } }"""
        )
    }

    fun delete(ingredient: Ingredient) {
        val id = ingredient.id

        db.ingredients.deleteMany(
            """{ "id" : $id }"""
        )
    }
}
