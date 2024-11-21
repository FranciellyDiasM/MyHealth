package br.com.quatrodcum.myhealth.view.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.R
import br.com.quatrodcum.myhealth.controller.SplashScreenController
import br.com.quatrodcum.myhealth.databinding.ActivitySplashScreenBinding
import br.com.quatrodcum.myhealth.model.dao.sqlite.DB
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.renderHtml
import br.com.quatrodcum.myhealth.view.login.LoginActivity
import br.com.quatrodcum.myhealth.view.menu.MenuActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenController by lazy { SplashScreenController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtMembers.text = getString(R.string.members).renderHtml()

        loadDatabase()
        loadUser()
    }

    private fun loadDatabase() {
        ThreadUtil.exec(
            doInBackground = {
                splashScreenController.getDatabase()
            },
            postExecuteTask = { database ->
                val databaseDetail = """
                    |${DB.USER.TABLE_NAME}: ${database.users.size}
                    |${DB.OBJECTIVE.TABLE_NAME}: ${database.objectives.size}
                    |${DB.MEAL.TABLE_NAME}: ${database.meals.size}
                    |${DB.INGREDIENT.TABLE_NAME}: ${database.ingredients.size}
                    |${DB.UNIT_OF_MEASUREMENT.TABLE_NAME}: ${database.unitOfMeasurements.size}
                    |${DB.INGREDIENT_MEAL.TABLE_NAME}: ${database.ingredientMeals.size}
                """.trimMargin()

                binding.txtDatabaseDetails.text = databaseDetail
            }
        )
    }

    private fun loadUser() {
        ThreadUtil.exec(
            doInBackground = {
                splashScreenController.loadLoggedUser()
            },
            postExecuteTask = { user ->
                binding.btnNext.setOnClickListener {
                    navigate(user)
                }
            }
        )
    }

    private fun navigate(user: User?) {
        if (user == null) {
            LoginActivity.startActivity(this)
            finish()
        } else {
            MenuActivity.startActivity(this)
            finish()
        }
    }
}