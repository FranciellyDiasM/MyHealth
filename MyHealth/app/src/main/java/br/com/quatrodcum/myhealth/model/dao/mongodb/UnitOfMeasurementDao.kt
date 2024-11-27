package br.com.quatrodcum.myhealth.model.dao.mongodb

import br.com.quatrodcum.myhealth.db
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class UnitOfMeasurementDao() {

    fun getAll(): List<UnitOfMeasurement> {
        return db.unitOfMeasurements.find()
    }

    fun insert(unitOfMeasurement: UnitOfMeasurement) {
        val name = unitOfMeasurement.name

        db.unitOfMeasurements.insertOne("""
            { "name" : "$name" }
        """)
    }

    fun update(unitOfMeasurement: UnitOfMeasurement) {
        val id = unitOfMeasurement.id
        val name = unitOfMeasurement.name

        db.unitOfMeasurements.updateMany(
            """{ "id" : $id }""",
            """{ "set" : { "name" : "$name" } }"""
        )
    }

    fun delete(unitOfMeasurement: UnitOfMeasurement) {
        val id = unitOfMeasurement.id

        db.unitOfMeasurements.deleteMany(
            """{ "id" : $id }"""
        )
    }
}
