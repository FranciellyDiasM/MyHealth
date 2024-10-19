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
import br.com.quatrodcum.myhealth.util.showYesNoDialog
import br.com.quatrodcum.myhealth.view.config.UpdateOrDeleteDialogFragment

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

        adapter.setOnItemClickListener { item ->
            val dialog = UpdateOrDeleteDialogFragment.newInstance(
                title = item.name,
                message = "O que deseja fazer com a unidade de medida: ${item.name}?"
            )

            dialog.setOnUpdateClickListener {
                showUpdateItemDialog(item)
            }

            dialog.setOnDeleteClickListener {
                showDeleteItemDialog(item)
            }

            dialog.show(supportFragmentManager, this::class.java.simpleName)
        }
    }

    private fun showUpdateItemDialog(unitOfMeasurement: UnitOfMeasurement) {
        showInputDialog(
            title = "Alterar",
            message = "Alterar ${unitOfMeasurement.name} para:",
            default = unitOfMeasurement.name,
            actionButton = { userInput ->
                val newUnitOfMeasurement = unitOfMeasurement.copy(name = userInput)
                updateUnitOfMeasurement(newUnitOfMeasurement)
            }
        )
    }

    private fun showDeleteItemDialog(unitOfMeasurement: UnitOfMeasurement) {
        val inUsed = configController.checkIfUsed(unitOfMeasurement)
        val message = if(inUsed) {
            "${unitOfMeasurement.name} esta sendo usado, esta ação vai remover ele e todos os itens de receita relacionados"
        } else {
            "Esta ação vai remover ${unitOfMeasurement.name}"
        }

        showYesNoDialog(
            title = "Remover",
            message = message,
            positiveButtonText = "Continuar!",
            negativeButtonText = "Cancelar",
            actionButton = {
                deleteUnitOfMeasurement(unitOfMeasurement)
            }
        )
    }

    private fun updateUnitOfMeasurement(unitOfMeasurement: UnitOfMeasurement) {
        ThreadUtil.exec(
            doInBackground = {
                configController.update(unitOfMeasurement)
            },
            postExecuteTask = {
                adapter.submitItem(unitOfMeasurement)
            }
        )
    }

    private fun deleteUnitOfMeasurement(unitOfMeasurement: UnitOfMeasurement) {
        ThreadUtil.exec(
            doInBackground = {
                configController.delete(unitOfMeasurement)
            },
            postExecuteTask = {
                adapter.remove(unitOfMeasurement)
            }
        )
    }

    private fun insertUnitOfMeasurement(name: String) {
        ThreadUtil.exec(
            doInBackground = {
                val unitOfMeasurement = UnitOfMeasurement(id = null, name = name)
                configController.insert(unitOfMeasurement)
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