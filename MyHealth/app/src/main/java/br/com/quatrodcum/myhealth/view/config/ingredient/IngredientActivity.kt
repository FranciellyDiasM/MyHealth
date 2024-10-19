package br.com.quatrodcum.myhealth.view.config.ingredient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.ConfigController
import br.com.quatrodcum.myhealth.databinding.ActivityIngredientBinding
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showInputDialog
import br.com.quatrodcum.myhealth.util.showYesNoDialog
import br.com.quatrodcum.myhealth.view.config.UpdateOrDeleteDialogFragment

class IngredientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngredientBinding
    private val configController by lazy { ConfigController(this) }
    private val adapter by lazy { IngredientAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientBinding.inflate(layoutInflater)

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
        binding.recIngredients.adapter = adapter
    }


    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            showInputDialog(
                title = "Novo ingrediente",
                message = "Insira o nome do novo ingrediente",
                actionButton = { userInput ->
                    insertItem(userInput)
                }
            )
        }

        adapter.setOnItemClickListener { item ->
            val dialog = UpdateOrDeleteDialogFragment.newInstance(
                title = item.name,
                message = "O que deseja fazer com o ingrediente? ${item.name}?"
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

    private fun showUpdateItemDialog(item: Ingredient) {
        showInputDialog(
            title = "Alterar",
            message = "Alterar ${item.name} para:",
            default = item.name,
            actionButton = { userInput ->
                val newItem = item.copy(name = userInput)
                updateItem(newItem)
            }
        )
    }

    private fun showDeleteItemDialog(item: Ingredient) {
        val inUsed = configController.checkIfUsed(item)
        val message = if(inUsed) {
            "${item.name} esta sendo em alguma refeição, esta ação vai remover todos os ingredientes relacionados"
        } else {
            "Esta ação vai remover ${item.name}"
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

    private fun updateItem(item: Ingredient) {
        ThreadUtil.exec(
            doInBackground = {
                configController.update(item)
            },
            postExecuteTask = {
                adapter.submitItem(item)
            }
        )
    }

    private fun deleteItem(item: Ingredient) {
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
                val item = Ingredient(id = null, name = name)
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
                configController.getIngredients()
            },
            postExecuteTask = { items ->
                adapter.submitList(items)
            }
        )
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, IngredientActivity::class.java)
            context.startActivity(intent)
        }
    }
}