package br.com.quatrodcum.myhealth.util

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.renderHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}