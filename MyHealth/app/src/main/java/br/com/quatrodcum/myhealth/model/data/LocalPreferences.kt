package br.com.quatrodcum.myhealth.model.data

import android.content.Context
import android.content.SharedPreferences
import br.com.quatrodcum.myhealth.model.domain.Login

class LocalPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "login_prefs"
        private const val KEY_LOGIN = "login"
        private const val KEY_PASSWORD = "password"
    }

    fun saveLogin(login: Login) {
        with(sharedPreferences.edit()) {
            putString(KEY_LOGIN, login.email)
            putString(KEY_PASSWORD, login.password)
            apply()
        }
    }

    fun getLogin(): Login? {
        val email = sharedPreferences.getString(KEY_LOGIN, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)

        return if (email != null && password != null) {
            Login(email, password)
        } else {
            null
        }
    }

    fun clearLogin() {
        with(sharedPreferences.edit()) {
            remove(KEY_LOGIN)
            remove(KEY_PASSWORD)
            apply()
        }
    }

}