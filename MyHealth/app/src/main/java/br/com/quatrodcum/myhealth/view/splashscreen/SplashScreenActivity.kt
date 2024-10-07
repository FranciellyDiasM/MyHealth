package br.com.quatrodcum.myhealth.view.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.SplashScreenController
import br.com.quatrodcum.myhealth.databinding.ActivitySplashScreenBinding
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.view.login.LoginActivity
import br.com.quatrodcum.myhealth.view.menu.MenuActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val splashScreenController by lazy { SplashScreenController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThreadUtil.exec(
            doInBackground = {
                Thread.sleep(SPLASH_TIME_OUT)
                splashScreenController.loadLoggedUser()
            },
            postExecuteTask = { user ->
                navigate(user)
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

    companion object {
        const val SPLASH_TIME_OUT = 3000L
    }
}