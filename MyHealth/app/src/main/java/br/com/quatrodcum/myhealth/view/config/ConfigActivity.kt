package br.com.quatrodcum.myhealth.view.config

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.databinding.ActivityConfigBinding
import br.com.quatrodcum.myhealth.view.config.unitofmeasurement.UnitOfMeasurementActivity

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupListeners() {
        binding.groupUnitOfMeasurement.setOnClickListener {
            UnitOfMeasurementActivity.startActivity(this)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ConfigActivity::class.java)
            context.startActivity(intent)
        }
    }
}