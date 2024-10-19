package br.com.quatrodcum.myhealth.view.meal.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import br.com.quatrodcum.myhealth.R
import br.com.quatrodcum.myhealth.controller.MealController
import br.com.quatrodcum.myhealth.databinding.ActivityMealDetailBinding
import br.com.quatrodcum.myhealth.model.domain.Ingredient
import br.com.quatrodcum.myhealth.model.domain.IngredientMeal
import br.com.quatrodcum.myhealth.model.domain.Meal
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.UnitOfMeasurement
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showYesNoDialog
import br.com.quatrodcum.myhealth.util.toast
import br.com.quatrodcum.myhealth.view.comboboxobjective.ObjectiveAdapter
import br.com.quatrodcum.myhealth.view.config.UpdateOrDeleteDialogFragment
import br.com.quatrodcum.myhealth.view.meal.detail.ingredientmeal.IngredientMealDialogFragment

class MealDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailBinding
    private val mealController by lazy { MealController(this) }
    private val mealId: Int by lazy { getMealId(intent) }

    private var objectives: List<Objective> = emptyList()
    private var ingredients: List<Ingredient> = emptyList()
    private var unitOfMeasurements: List<UnitOfMeasurement> = emptyList()

    private val ingredientMealAdapter = IngredientMealAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
        recoveryExtra()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                setFieldsStatus(true)
                true
            }

            R.id.action_delete -> {
                showDeleteDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        binding.recIngredients.adapter = ingredientMealAdapter
        ingredientMealAdapter.onItemClickListener { item ->
            showIngredientActionDialog(item)
        }

        ThreadUtil.exec(
            doInBackground = {
                val objectives = mealController.getAllObjectives()
                val ingredients = mealController.getAllIngredients()
                val unitsOfMeasurement = mealController.getAllUnitOfMeasurements()

                DatabaseValues(objectives, ingredients, unitsOfMeasurement)
            },
            postExecuteTask = { databaseValues ->
                this.objectives = databaseValues.objectives
                this.ingredients = databaseValues.ingredients
                this.unitOfMeasurements = databaseValues.unitOfMeasurements

                val adapter = ObjectiveAdapter(this, objectives)
                binding.cbxObjective.adapter = adapter
            }
        )

        binding.btnAddIngredient.setOnClickListener {
            showAddIngredientDialog()
        }

        binding.btnSaveMeal.setOnClickListener {
            val meal = createMeal()
            if (mealId > 0) {
                update(meal)
            } else {
                insert(meal)
            }

        }
    }

    private fun recoveryExtra() {
        if (mealId > 0) {
            ThreadUtil.exec(
                doInBackground = {
                    mealController.getMeal(mealId)
                },
                postExecuteTask = { meal ->
                    meal?.let {
                        loadMeal(meal)
                    }
                }
            )
        } else {
            setFieldsStatus(true)
        }
    }

    private fun loadMeal(meal: Meal) {
        setFieldsStatus(false)

        binding.edtName.setText(meal.name)
        binding.edtCalories.setText(meal.calories.toString())
        binding.edtDescription.setText(meal.description)
        binding.edtPreparationMode.setText(meal.preparationMode)

        val objectivePosition = objectives.indexOfFirst { it.id == meal.objective.id }

        if (objectivePosition != -1) {
            binding.cbxObjective.setSelection(objectivePosition)
        }

        ingredientMealAdapter.submitList(meal.ingredients)
    }

    private fun insert(meal: Meal) {
        ThreadUtil.exec(
            doInBackground = {
                mealController.insert(meal)
            },
            postExecuteTask = {
                toast("Salvo com sucesso!")
                finishAndRefresh()
            }
        )
    }

    private fun update(meal: Meal) {
        ThreadUtil.exec(
            doInBackground = {
                mealController.update(meal)
            },
            postExecuteTask = {
                toast("Salvo com sucesso!")
                finishAndRefresh()
            }
        )
    }

    private fun finishAndRefresh() {
        setResult(RESULT_OK)
        finish()
    }

    private fun createMeal(): Meal {
        val name = binding.edtName.text.toString()
        val calories = binding.edtCalories.text.toString().toDoubleOrNull() ?: 0.0
        val description = binding.edtDescription.text.toString()
        val indexObjective = binding.cbxObjective.selectedItemPosition
        val objective = objectives[indexObjective]
        val preparationMode = binding.edtPreparationMode.text.toString()
        val ingredients = ingredientMealAdapter.currentList

        return Meal(
            id = mealId,
            name = name,
            calories = calories,
            description = description,
            objective = objective,
            preparationMode = preparationMode,
            ingredients = ingredients
        )
    }

    private fun showAddIngredientDialog() {
        val dialog = IngredientMealDialogFragment.newInstance(ingredients, unitOfMeasurements)
        dialog.setOnConfirmListener { item ->
            ingredientMealAdapter.submitItem(item)
        }

        dialog.show(supportFragmentManager, this::class.java.simpleName)
    }


    private fun removeIngredientMeal(item: IngredientMeal) {
        ingredientMealAdapter.remove(item)
    }

    private fun showIngredientActionDialog(item: IngredientMeal) {
        val dialog = UpdateOrDeleteDialogFragment.newInstance(
            title = item.ingredient.name,
            message = "Deseja remover ${item.ingredient.name} da sua receita?",
            showUpdateButton = false
        )

        dialog.setOnDeleteClickListener {
            removeIngredientMeal(item)
        }

        dialog.show(supportFragmentManager, this::class.java.simpleName)
    }

    private fun setFieldsStatus(editing: Boolean) {
        binding.edtName.isEnabled = editing
        binding.edtCalories.isEnabled = editing
        binding.edtDescription.isEnabled = editing
        binding.edtPreparationMode.isEnabled = editing

        binding.cbxObjective.isEnabled = editing
        ingredientMealAdapter.isEnabled = editing

        binding.btnAddIngredient.isVisible = editing
        binding.btnSaveMeal.isVisible = editing
    }

    private fun showDeleteDialog() {
        val mealName = binding.edtName.text.toString()

        showYesNoDialog(
            title = "Remover",
            message = "Esta ação vai remover $mealName",
            positiveButtonText = "Continuar!",
            negativeButtonText = "Cancelar",
            actionButton = {
                deleteItem(mealId)
            }
        )
    }

    private fun deleteItem(mealId: Int) {
        if (mealId > 0) {
            ThreadUtil.exec(
                doInBackground = {
                    mealController.deleteMeal(mealId)
                },
                postExecuteTask = {
                    finishAndRefresh()
                }
            )
        } else {
            finish()
        }
    }

    companion object {
        fun startActivity(
            context: Context,
            mealId: Int = -1
        ) {
            val intent = Intent(context, MealDetailActivity::class.java).apply {
                putExtra(EXTRA_MEAL_ID, mealId)
            }

            context.startActivity(intent)
        }

        private fun getMealId(intent: Intent): Int {
            return intent.getIntExtra(EXTRA_MEAL_ID, -1)
        }

        private const val EXTRA_MEAL_ID = "mealId"
    }

    data class DatabaseValues(
        val objectives: List<Objective>,
        val ingredients: List<Ingredient>,
        val unitOfMeasurements: List<UnitOfMeasurement>
    )
}