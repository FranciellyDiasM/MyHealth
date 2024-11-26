package br.com.quatrodcum.myhealth.util

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    log(message)
}

fun log(message: String) {
    Log.i("MyHealthLog", message)

    if (message.length > 4000) {
        var start = 0
        var end: Int
        while (start < message.length) {
            end = (start + 4000).coerceAtMost(message.length)
            Log.i("MyHealthLog", message.substring(start, end))
            start = end
        }
    } else {
        Log.i("MyHealthLog", message)
    }
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
    default: String = "",
    actionButton: (String) -> Unit = {}
) {
    val input = AppCompatEditText(this)
    input.setText(default)

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

fun Context.showYesNoDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    actionButton: () -> Unit
) {
    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            actionButton()
            dialog.dismiss()
        }
        .setNegativeButton(negativeButtonText) { dialog, _ ->
            dialog.cancel()
        }
        .create()

    dialog.show()
}