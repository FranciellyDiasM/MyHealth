package br.com.quatrodcum.myhealth.view.meal.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.MealController
import br.com.quatrodcum.myhealth.databinding.ActivityMealBinding
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.log
import br.com.quatrodcum.myhealth.view.meal.detail.MealDetailActivity

class MealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private val mealController by lazy { MealController(this) }
    private val adapter by lazy { MealAdapter() }

    private val objectiveId: Int by lazy { getObjectiveId(intent) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupViews()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupViews() {
        binding.recMeal.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            MealDetailActivity.startActivity(this)
        }

        adapter.setOnItemClickListener { item ->
            MealDetailActivity.startActivity(this, item.id ?: -1)
        }
    }

    private fun loadData() {
        ThreadUtil.exec(
            doInBackground = {
                if(objectiveId > 0) {
                    mealController.getAllMealByObjective(objectiveId)
                } else {
                    mealController.getAllMeal()
                }
            },
            postExecuteTask = { items ->
                adapter.submitList(items)
                log(items.toString())
            }
        )
    }

    companion object {
        fun startActivity(context: Context, objectiveId: Int = -1) {
             val intent = Intent(context, MealsActivity::class.java).apply {
                putExtra(EXTRA_OBJECTIVE_ID, objectiveId)
            }

            context.startActivity(intent)
        }

        private fun getObjectiveId(intent: Intent): Int {
            return intent.getIntExtra(EXTRA_OBJECTIVE_ID, -1)
        }

        private const val EXTRA_OBJECTIVE_ID = "mealId"
    }

}