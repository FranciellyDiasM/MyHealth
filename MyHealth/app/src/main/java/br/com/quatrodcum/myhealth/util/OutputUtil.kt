package br.com.quatrodcum.myhealth.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    log(message)
}

fun log(message: String) {
    Log.i("MyHealthLog", message)
}

fun Context.showSimpleDialog(
    title: String,
    message: String,
    textButton: String = "Entendi",
    actionButton: () -> Unit = {}
) {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(message)

    builder.setPositiveButton(textButton) { dialog, _ ->
        actionButton.invoke()
        dialog.dismiss()
    }

    val dialog: AlertDialog = builder.create()
    dialog.show()
}