package br.com.quatrodcum.myhealth.view.login

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.LoginController
import br.com.quatrodcum.myhealth.databinding.ActivityLoginBinding
import br.com.quatrodcum.myhealth.model.data.Session
import br.com.quatrodcum.myhealth.model.domain.Login
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.showSimpleDialog
import br.com.quatrodcum.myhealth.view.menu.MenuActivity
import br.com.quatrodcum.myhealth.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginController by lazy { LoginController(this) }

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        binding.btnRegister.paintFlags = binding.btnRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setupListeners() {
        startForResult = RegisterActivity.registerForActivityResult(
            activity = this,
            callback = { login ->
                login?.let { safeLogin ->
                    binding.edtEmail.setText(safeLogin.email)
                    binding.edtPassword.setText(safeLogin.password)
                }
            }
        )

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            ThreadUtil.exec(
                doInBackground = {
                    loginController.getUserByEmailAndPassword(email, password)
                },
                postExecuteTask = { user ->
                    handlerLogin(user)
                }
            )
        }

        binding.btnRegister.setOnClickListener {
            RegisterActivity.startActivity(this, startForResult)
        }
    }

    private fun handlerLogin(user: User?) {
        Session.currentUser = user

        if (user == null) {
            showSimpleDialog(
                "Login Inválido",
                "Alguma das credenciais estão inválidas, revise o e-mail e senha digitados"
            )
        } else {
            val email = user.email
            val password = user.password

            val login = Login(email, password)

            loginController.saveCurrentLogin(login)
            MenuActivity.startActivity(this)
            finish()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}