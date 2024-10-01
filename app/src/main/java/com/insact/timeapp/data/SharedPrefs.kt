package com.insact.timeapp.data

import android.content.Context
import com.insact.timeapp.utils.EMPTY_STRING

class SharedPrefs(context: Context) {
    private val sPrefs = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    suspend fun getSelectedCapital(): String = sPrefs.getString(CAPITAL_KEY, EMPTY_STRING).orEmpty()

    suspend fun setSelectedCapital(capital: String) =
        sPrefs.edit()
            .putString(CAPITAL_KEY, capital)
            .apply()

    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS_NAME"
        private const val CAPITAL_KEY = "CAPITAL_KEY"
    }
}