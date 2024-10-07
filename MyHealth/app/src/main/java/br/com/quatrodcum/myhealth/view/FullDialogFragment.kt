package br.com.quatrodcum.myhealth.view

import android.view.WindowManager
import androidx.fragment.app.DialogFragment

open class FullDialogFragment : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}