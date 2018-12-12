package co.id.billyon.repository.cashier.login

import android.content.SharedPreferences
import co.id.billyon.util.PREF_KEY_CASHIER
import co.id.billyon.util.PREF_KEY_LOGIN
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(private val editor: SharedPreferences.Editor, private val preferences: SharedPreferences) {

    fun setLoggedIn(isLoggedIn : Boolean) {
        editor.putBoolean(PREF_KEY_LOGIN, isLoggedIn)
        editor.apply()
    }
    fun isLoggedIn() = preferences.getBoolean(PREF_KEY_LOGIN, false)

    fun setAsCashier(isCashier : Boolean) {
        editor.putBoolean(PREF_KEY_CASHIER, isCashier)
        editor.apply()
    }
    fun isCashier() = preferences.getBoolean(PREF_KEY_CASHIER, false)

}