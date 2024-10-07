package br.com.quatrodcum.myhealth.util

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.clearError() {
    this.error = null
    this.isErrorEnabled = false
}