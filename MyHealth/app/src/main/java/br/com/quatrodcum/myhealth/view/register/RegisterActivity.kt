package br.com.quatrodcum.myhealth.view.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.RegisterController
import br.com.quatrodcum.myhealth.databinding.ActivityRegisterBinding
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil
import br.com.quatrodcum.myhealth.util.toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerController = RegisterController(this)
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
            val objective =  objectives[indexObjective]
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
                }
            )
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}