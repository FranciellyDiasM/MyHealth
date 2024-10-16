package br.com.quatrodcum.myhealth.view.config

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.ConfigController
import br.com.quatrodcum.myhealth.databinding.ActivityConfigBinding
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showYesNoDialog
import br.com.quatrodcum.myhealth.util.toast
import br.com.quatrodcum.myhealth.view.config.ingredient.IngredientActivity
import br.com.quatrodcum.myhealth.view.config.objective.ObjectiveActivity
import br.com.quatrodcum.myhealth.view.config.unitofmeasurement.UnitOfMeasurementActivity
import br.com.quatrodcum.myhealth.view.meal.MealActivity

class ConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigBinding
    private val configController by lazy { ConfigController(this) }

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

        binding.groupObjective.setOnClickListener {
            ObjectiveActivity.startActivity(this)
        }

        binding.groupIngredient.setOnClickListener {
            IngredientActivity.startActivity(this)
        }

        binding.groupMeal.setOnClickListener {
            val intent = Intent(this, MealActivity::class.java)
            startActivity(intent)
        }

        binding.groupClear.setOnClickListener {
            showYesNoDialog(
                title = "Limpar banco",
                message = "Esta ação vai limpar todos os dados do banco de dados",
                positiveButtonText = "Confirmar",
                negativeButtonText = "Cancelar",
                actionButton = {
                    dropDatabase()
                }
            )
        }
    }

    private fun dropDatabase() {
        ThreadUtil.exec(
            doInBackground = {
                configController.dropDatabase()
            }
        )
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ConfigActivity::class.java)
            context.startActivity(intent)
        }
    }
}