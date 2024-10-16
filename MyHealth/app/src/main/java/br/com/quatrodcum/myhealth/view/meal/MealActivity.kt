package br.com.quatrodcum.myhealth.view.meal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.MealController
import br.com.quatrodcum.myhealth.databinding.ActivityMealBinding
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showInputDialog
import br.com.quatrodcum.myhealth.util.toast
import br.com.quatrodcum.myhealth.view.config.UpdateOrDeleteDialogFragment

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private val mealController by lazy { MealController(this) }
    private val adapter by lazy { MealAdapter() }

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
        binding.btnAdd.setOnClickListener {
            toast("A implementar")
        }

        adapter.setOnItemClickListener { item ->
            val dialog = UpdateOrDeleteDialogFragment.newInstance(
                title = item.name,
                message = "O que deseja fazer com o ingrediente? ${item.name}?"
            )

            dialog.setOnUpdateClickListener {
                toast("a implementar")
            }

            dialog.setOnDeleteClickListener {
                toast("a implementar")
            }

            dialog.show(supportFragmentManager, this::class.java.simpleName)
        }
    }

    private fun loadData() {
        ThreadUtil.exec(
            doInBackground = {
                mealController.getAllMeal()
            },
            postExecuteTask = { items ->
                adapter.submitList(items)
            }
        )
    }

}