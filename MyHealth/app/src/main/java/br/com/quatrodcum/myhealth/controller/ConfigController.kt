package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.ObjectiveDao
import br.com.quatrodcum.myhealth.model.dao.UnitOfMeasurementDao
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class ConfigController(context: Context) {

    private val unitOfMeasurementDao: UnitOfMeasurementDao = UnitOfMeasurementDao(context)
    private val objectiveDao: ObjectiveDao = ObjectiveDao(context)

    fun getUnitOfMeasurements() : List<UnitOfMeasurement>{
        return unitOfMeasurementDao.getAll()
    }

    fun insert(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.insert(unitOfMeasurement)
    }

    fun update(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.update(unitOfMeasurement)
    }

    fun delete(unitOfMeasurement: UnitOfMeasurement) {
        unitOfMeasurementDao.delete(unitOfMeasurement)
    }

    fun getObjectives() : List<Objective>{
        return objectiveDao.getAllObjectives()
    }

    fun insert(objective: Objective) {
        objectiveDao.insert(objective)
    }

    fun update(objective: Objective) {
        objectiveDao.update(objective)
    }

    fun delete(objective: Objective) {
        objectiveDao.delete(objective)
    }
}