package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.dao.UnitOfMeasurementDao
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement

class ConfigController(context: Context) {

    private val unitOfMeasurementDao: UnitOfMeasurementDao = UnitOfMeasurementDao(context)

    fun getUnitOfMeasurements() : List<UnitOfMeasurement>{
        return unitOfMeasurementDao.getAll()
    }
}