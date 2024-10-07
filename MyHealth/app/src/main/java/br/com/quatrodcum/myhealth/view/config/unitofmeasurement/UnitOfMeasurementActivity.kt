package br.com.quatrodcum.myhealth.view.config.unitofmeasurement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.databinding.ActivityUnitOfMeasurementBinding

class UnitOfMeasurementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitOfMeasurementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitOfMeasurementBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, UnitOfMeasurementActivity::class.java)
            context.startActivity(intent)
        }
    }
}