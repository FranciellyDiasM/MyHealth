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
import br.com.quatrodcum.myhealth.util.clearError
import br.com.quatrodcum.myhealth.view.comboboxobjective.ObjectiveAdapter
import java.util.Locale

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
            registerUser()
        }

        binding.btnImc.setOnClickListener {
            showImcCalculatorDialog()
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

    private fun registerUser() {
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

        if(validateUserFields(user).not()) {
            return
        }

        ThreadUtil.exec(
            doInBackground = {
                registerController.insertUser(user)
            },
            postExecuteTask = {
                finishWithResult()
            }
        )
    }

    private fun validateUserFields(user:  User) : Boolean {
        var successful = true

        if(user.name.length < 3) {
            successful = false
            binding.lblName.error = "Nome muito curto"
        } else {
            binding.lblName.error = null
            binding.lblName.clearError()
        }

        if(user.email.length < 3 || user.email.contains("@").not()) {
            successful = false
            binding.lblEmail.error = "Email Inválido"
        } else {
            binding.lblEmail.clearError()
        }

        if(user.password.length < 3) {
            successful = false
            binding.lblPassword.error = "Senha muito curta"
        } else {
            binding.lblPassword.clearError()
        }

        if(user.imc < 1) {
            successful = false
            binding.lblImc.error = "IMC inválido"
        } else {
            binding.lblImc.clearError()
        }

        return successful
    }

    private fun showImcCalculatorDialog() {
        val dialog = ImcCalculatorDialogFragment.newInstance()
        dialog.setOnConfirmListener { form ->
            val imc = registerController.calculateImc(form.height, form.weight)
            binding.edtImc.setText(String.format(Locale.US, "%.1f", imc))
        }

        dialog.show(supportFragmentManager, this::class.java.simpleName)
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