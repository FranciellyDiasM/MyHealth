package br.com.quatrodcum.myhealth.view.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.quatrodcum.myhealth.databinding.FragmentImcCalculatorDialogBinding
import br.com.quatrodcum.myhealth.view.FullDialogFragment

class ImcCalculatorDialogFragment private constructor() : FullDialogFragment() {

    private lateinit var binding: FragmentImcCalculatorDialogBinding
    private var _onConfirmListener: (Form) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImcCalculatorDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        binding.btnCalculate.setOnClickListener {
            val weight = binding.edtWeight.text.toString().toDoubleOrNull() ?: 0.0
            val height = binding.edtHeight.text.toString().toDoubleOrNull() ?: 0.0

            if(weight > 1 && height > 1) {
                val form = Form(weight, height)
                _onConfirmListener.invoke(form)
            }

            dismiss()
        }
    }

    fun setOnConfirmListener(action: (Form) -> Unit) {
        this._onConfirmListener = action
    }

    companion object {
        fun newInstance(): ImcCalculatorDialogFragment {
            return ImcCalculatorDialogFragment()
        }
    }

    data class Form(
        val weight: Double,
        val height: Double
    )
}