package br.com.quatrodcum.myhealth.util

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

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

fun Context.showInputDialog(
    title: String,
    message: String,
    actionButton: (String) -> Unit
) {
    val input = AppCompatEditText(this)

    val container = FrameLayout(this)
    val params = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )

    val marginInDp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics
    ).toInt()

    params.setMargins(marginInDp, 0, marginInDp, 0)
    input.layoutParams = params
    container.addView(input)

    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setView(container)
        .setPositiveButton("OK") { dialog, _ ->
            val userInput = input.text.toString()

            actionButton.invoke(userInput)
            dialog.dismiss()
        }
        .setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        .create()

    dialog.show()
}