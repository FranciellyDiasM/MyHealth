package br.com.quatrodcum.myhealth.view.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.quatrodcum.myhealth.controller.RegisterController
import br.com.quatrodcum.myhealth.databinding.ActivityRegisterBinding
import br.com.quatrodcum.myhealth.model.domain.Objective
import br.com.quatrodcum.myhealth.model.domain.User
import br.com.quatrodcum.myhealth.util.ThreadUtil

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
    }

    private fun setupView() {

        ThreadUtil.exec(
            doInBackground = registerController::getAllObjectives,
            postExecuteTask = ::loadObjectives
        )

        val items = arrayOf("1", "2")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.cbxObjective.adapter = adapter
    }

    private fun loadObjectives(objectives: List<Objective>) {
        this.objectives = objectives

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            objectives.map { it.description })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.cbxObjective.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val indexObjective = binding.cbxObjective.selectedItemPosition
            val objective =  objectives[indexObjective]

            val user = User(
                id = null,
                name = name,
                email = email,
                objective = objective
            )

            Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }
}