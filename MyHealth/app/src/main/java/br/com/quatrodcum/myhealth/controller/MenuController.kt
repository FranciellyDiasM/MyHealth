package br.com.quatrodcum.myhealth.controller

import android.content.Context
import br.com.quatrodcum.myhealth.model.data.LocalPreferences
import br.com.quatrodcum.myhealth.model.data.Session

class MenuController(context: Context) {
    private val localPreferences = LocalPreferences(context)

    fun logout() {
        localPreferences.clearLogin()
        Session.currentUser = null
    }
}