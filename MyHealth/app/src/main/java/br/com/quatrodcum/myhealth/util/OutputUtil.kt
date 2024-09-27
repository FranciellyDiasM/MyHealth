package br.com.quatrodcum.myhealth.util

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    log(message)
}

fun log(message: String) {
    Log.i("MyHealthLog", message)
}