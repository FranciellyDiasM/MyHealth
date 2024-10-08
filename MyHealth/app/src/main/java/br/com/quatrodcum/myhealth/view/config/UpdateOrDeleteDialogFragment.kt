package br.com.quatrodcum.myhealth.view.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.quatrodcum.myhealth.databinding.DialogCrudBinding
import br.com.quatrodcum.myhealth.view.FullDialogFragment

class UpdateOrDeleteDialogFragment private constructor() : FullDialogFragment() {

    private lateinit var binding: DialogCrudBinding
    private var _onUpdateClickListener: () -> Unit = {}
    private var _onDeleteClickListener: () -> Unit = {}

    private var title: String = ""
    private var message: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        binding.txtTitle.text = title
        binding.txtMessage.text = message

        binding.btnUpdate.setOnClickListener {
            _onUpdateClickListener.invoke()
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            _onDeleteClickListener.invoke()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    fun setOnUpdateClickListener(action: () -> Unit) {
        this._onUpdateClickListener = action
    }

    fun setOnDeleteClickListener(action: () -> Unit) {
        this._onDeleteClickListener = action
    }

    companion object {
        fun newInstance(
            title: String,
            message: String
        ): UpdateOrDeleteDialogFragment {
            return UpdateOrDeleteDialogFragment().apply {
                this.title = title
                this.message = message
            }
        }
    }

}