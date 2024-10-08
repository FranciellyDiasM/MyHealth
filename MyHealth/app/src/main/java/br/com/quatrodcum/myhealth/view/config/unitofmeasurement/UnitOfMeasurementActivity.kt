package br.com.quatrodcum.myhealth.view.config.unitofmeasurement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.ConfigController
import br.com.quatrodcum.myhealth.databinding.ActivityUnitOfMeasurementBinding
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showInputDialog

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
        setupListeners()
        loadData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupViews() {
        binding.recUnitOfMeasurement.adapter = adapter
    }


    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            showInputDialog(
                title = "Nova unidade de medida",
                message = "Insira o nome da nova unidade de medida",
                actionButton = { userInput ->
                    insertUnitOfMeasurement(userInput)
                }
            )
        }
    }

    private fun insertUnitOfMeasurement(name: String) {
        ThreadUtil.exec(
            doInBackground = {
                val unitOfMeasurement = UnitOfMeasurement(id = null, name = name)
                configController.insertUnitOfMeasurement(unitOfMeasurement)
            },
            postExecuteTask = {
                loadData()
            }
        )

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