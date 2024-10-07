package br.com.quatrodcum.myhealth.view.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.RegisterController
import br.com.quatrodcum.myhealth.databinding.ActivityRegisterBinding
import br.com.quatrodcum.myhealth.model.domain.Login
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerController by lazy { RegisterController(this) }
    private var objectives: List<Objective> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupView()
        setupListeners()
        setupToolbar()
    }

    private fun setupView() {
        ThreadUtil.exec(
            doInBackground = registerController::getAllObjectives,
            postExecuteTask = ::loadObjectives
        )
    }

    private fun loadObjectives(objectives: List<Objective>) {
        this.objectives = objectives
        val adapter = ObjectiveAdapter(this, objectives)

        binding.cbxObjective.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val indexObjective = binding.cbxObjective.selectedItemPosition
            val objective = objectives[indexObjective]
            val imc = binding.edtImc.text.toString().toDoubleOrNull() ?: 0.0

            val user = User(
                id = null,
                name = name,
                email = email,
                objective = objective,
                password = password,
                imc = imc
            )

            ThreadUtil.exec(
                doInBackground = {
                    registerController.insertUser(user)
                },
                postExecuteTask = {
                    finishWithResult()
                }
            )
        }
    }

    private fun finishWithResult() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()


        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_EMAIL, email)
        resultIntent.putExtra(EXTRA_PASSWORD, password)

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    companion object {
        private const val EXTRA_EMAIL = "email"
        private const val EXTRA_PASSWORD = "password"

        fun registerForActivityResult(
            activity: ComponentActivity,
            callback: (Login?) -> Unit
        ): ActivityResultLauncher<Intent> {
            return activity.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val email = result.data?.getStringExtra(EXTRA_EMAIL)
                    val password = result.data?.getStringExtra(EXTRA_PASSWORD)

                    if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                        callback(null)
                    } else {
                        val login = Login(email, password)
                        callback(login)
                    }
                }
            }
        }

        fun startActivity(
            context: Context,
            startForResult: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, RegisterActivity::class.java)
            startForResult.launch(intent)
        }
    }
}