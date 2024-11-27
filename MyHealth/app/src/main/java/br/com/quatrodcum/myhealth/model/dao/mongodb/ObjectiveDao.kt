package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.Objective


class ObjectiveDao() {

    fun getAllObjectives(): List<Objective> {
        return db.objectives.find()
    }

    fun insert(objective: Objective) {
        val description = objective.description

        db.objectives.insertOne("""
            { "description" : "$description" }
        """)
    }

    fun update(objective: Objective) {
        val id = objective.id
        val description = objective.description

        db.objectives.updateMany(
            """{ "id" : $id }""",
            """{ "set" : { "description" : "$description" } }"""
        )
    }

    fun delete(objective: Objective) {
        val id = objective.id

        db.objectives.deleteMany("""{ "id" : $id }""")
        db.users.deleteMany(""" { "objective" :  $id } """)
        db.meals.deleteMany(""" { "objective" :  $id } """)
    }
}
