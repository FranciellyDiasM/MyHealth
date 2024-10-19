package br.com.quatrodcum.myhealth.view.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.R
import br.com.quatrodcum.myhealth.controller.MenuController
import br.com.quatrodcum.myhealth.databinding.ActivityMenuBinding
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.toast
import br.com.quatrodcum.myhealth.view.config.ConfigActivity
import br.com.quatrodcum.myhealth.view.login.LoginActivity
import br.com.quatrodcum.myhealth.view.meal.detail.MealDetailActivity
import br.com.quatrodcum.myhealth.view.meal.list.MealsActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private val menuController by lazy { MenuController(this) }
    private lateinit var user: User

    private val suggestionAdapter by lazy { SuggestionAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                ConfigActivity.startActivity(this)
                true
            }

            R.id.action_logout -> {
                logout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun loadData() {
        menuController.getCurrentUser()?.let { currentUser ->
            user = currentUser
            loadUser(user)
        } ?: run {
            LoginActivity.putOnTop(this@MenuActivity)
        }
    }

    private fun setupView() {
        binding.recSuggestions.adapter = suggestionAdapter
        suggestionAdapter.onItemClickListener { item ->
            MealDetailActivity.startActivity(this, item.id!!)
        }

        binding.btnSeeMore.setOnClickListener {
            MealsActivity.startActivity(this, user.objective.id ?: -1)
        }
    }

    private fun loadUser(user: User) {
        binding.txtWelcome.text = "Olá ${user.name}!"
        binding.txtObjective.text = "Vamos no foco para alcançar o teu objetivo ${user.objective.description}"

        ThreadUtil.exec(
            doInBackground = {
                menuController.getSuggestions(user.objective.id ?: -1)
            },
            postExecuteTask = { databaseMeals ->
                suggestionAdapter.submitList(databaseMeals)
            }
        )
    }

    private fun logout() {
        ThreadUtil.exec(
            doInBackground = {
                menuController.logout()
            },
            postExecuteTask = {
                LoginActivity.startActivity(this)
                finish()
            }
        )
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MenuActivity::class.java)
            context.startActivity(intent)
        }
    }
}