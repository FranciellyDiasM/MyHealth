package br.com.quatrodcum.myhealth.view.config.objective

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.ConfigController
import br.com.quatrodcum.myhealth.databinding.ActivityObjectiveBinding
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showInputDialog
import br.com.quatrodcum.myhealth.util.showYesNoDialog
import br.com.quatrodcum.myhealth.view.config.UpdateOrDeleteDialogFragment

class ObjectiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObjectiveBinding
    private val configController by lazy { ConfigController(this) }
    private val adapter by lazy { ObjectiveAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectiveBinding.inflate(layoutInflater)

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
        binding.recObjectives.adapter = adapter
    }


    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            showInputDialog(
                title = "Novo objetivo",
                message = "Insira o nome do novo objetivo",
                actionButton = { userInput ->
                    insertItem(userInput)
                }
            )
        }

        adapter.setOnItemClickListener { item ->
            val dialog = UpdateOrDeleteDialogFragment.newInstance(
                title = item.description,
                message = "O que deseja fazer com o objetivo? ${item.description}?"
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

    private fun showUpdateItemDialog(item: Objective) {
        showInputDialog(
            title = "Alterar",
            message = "Alterar ${item.description} para:",
            default = item.description,
            actionButton = { userInput ->
                val newItem = item.copy(description = userInput)
                updateItem(newItem)
            }
        )
    }

    private fun showDeleteItemDialog(item: Objective) {
        val inUsed = configController.checkIfUsed(item)
        val message = if(inUsed) {
            "${item.description} esta sendo usado, esta ação vai remover ele e todos os usuarios e refeições relacionados"
        } else {
            "Esta ação vai remover ${item.description}"
        }


        showYesNoDialog(
            title = "Remover",
            message = message,
            positiveButtonText = "Continuar!",
            negativeButtonText = "Cancelar",
            actionButton = {
                deleteItem(item)
            }
        )
    }

    private fun updateItem(item: Objective) {
        ThreadUtil.exec(
            doInBackground = {
                configController.update(item)
            },
            postExecuteTask = {
                adapter.submitItem(item)
            }
        )
    }

    private fun deleteItem(item: Objective) {
        ThreadUtil.exec(
            doInBackground = {
                configController.delete(item)
            },
            postExecuteTask = {
                adapter.remove(item)
            }
        )
    }

    private fun insertItem(name: String) {
        ThreadUtil.exec(
            doInBackground = {
                val item = Objective(id = null, description = name)
                configController.insert(item)
            },
            postExecuteTask = {
                loadData()
            }
        )

    }

    private fun loadData() {
        ThreadUtil.exec(
            doInBackground = {
                configController.getObjectives()
            },
            postExecuteTask = { items ->
                adapter.submitList(items)
            }
        )
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ObjectiveActivity::class.java)
            context.startActivity(intent)
        }
    }
}