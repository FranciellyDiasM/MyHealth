package br.com.quatrodcum.myhealth.view.config.unitofmeasurement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.ConfigController
import br.com.quatrodcum.myhealth.databinding.ActivityUnitOfMeasurementBinding
import br.com.quatrodcum.myhealth.util.ThreadUtil

class UnitOfMeasurementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnitOfMeasurementBinding
    private val configController by lazy { ConfigController(this) }
    private val adapter by lazy { UnitOfMeasurementAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitOfMeasurementBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupToolbar()
        setupViews()
        loadData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupViews() {
        binding.recUnitOfMeasurement.adapter = adapter
    }

    private fun loadData() {
        ThreadUtil.exec(
            doInBackground = {
                configController.getUnitOfMeasurements()
            },
            postExecuteTask = { unitOfMeasurements ->
                adapter.submitList(unitOfMeasurements)
            }
        )
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, UnitOfMeasurementActivity::class.java)
            context.startActivity(intent)
        }
    }
}