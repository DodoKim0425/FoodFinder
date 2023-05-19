package com.ssafy.foodfind

import android.content.Context
import android.content.SharedPreferences
import com.ssafy.foodfind.data.entity.User

object SharedPrefs {
    private const val LOGIN_SESSION = "login.session"
    private var user: User? = null

    fun openSharedPrep(context: Context): SharedPreferences {
        return context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun getToken() = user

    fun saveToken(user: User) {
        this.user = user
        val editor = App.prefs.edit()
        editor.putInt("userId", user.userId)
        editor.putString("username", user.username)
        editor.putString("phoneNumber", user.phoneNumber)
        editor.putString("userType", user.userType)
        editor.apply()
    }
}