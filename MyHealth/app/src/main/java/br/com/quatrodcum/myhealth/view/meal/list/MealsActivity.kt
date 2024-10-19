package br.com.quatrodcum.myhealth.view.meal.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
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

    private lateinit var startMealDetailForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealBinding.inflate(layoutInflater)
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
        binding.recMeal.adapter = adapter
    }

    private fun setupListeners() {
        startMealDetailForResult = MealDetailActivity.registerForActivityResult(
            activity = this,
            callback = { refresh ->
                if (refresh) {
                    loadData()
                }
            }
        )

        binding.btnAdd.setOnClickListener {
            MealDetailActivity.startActivity(this, startMealDetailForResult)
        }

        adapter.setOnItemClickListener { item ->
            MealDetailActivity.startActivity(this, startMealDetailForResult, item.id ?: -1)
        }
    }

    private fun loadData() {
        ThreadUtil.exec(
            doInBackground = {
                mealController.getAllMeal()
            },
            postExecuteTask = { items ->
                adapter.submitList(items)
                log(items.toString())
            }
        )
    }

}