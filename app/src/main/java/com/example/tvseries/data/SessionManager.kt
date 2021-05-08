package com.example.tvseries.data

import android.content.Context
import android.content.SharedPreferences
import com.example.tvseries.R

class SessionManager(context: Context) {
    companion object {
        const val TOKEN = "token"
        const val UID = "uid"
        const val CLIENT = "client"
    }

    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    fun saveSession(token: String?, uid: String?, client: String?) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.putString(UID, uid)
        editor.putString(CLIENT, client)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(TOKEN, null)
    }

    fun getUid(): String? {
        return prefs.getString(UID, null)
    }

    fun getClient(): String? {
        return prefs.getString(CLIENT, null)
    }
}