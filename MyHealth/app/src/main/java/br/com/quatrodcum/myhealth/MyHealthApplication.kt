package br.com.quatrodcum.myhealth

import android.app.Application
import br.com.quatrodcum.myhealth.model.dao.mongodb.Database

var db: Database = Database()
    private set

class MyHealthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        db.initialize(this)
    }
}