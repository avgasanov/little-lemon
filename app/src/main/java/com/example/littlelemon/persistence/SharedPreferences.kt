package com.example.littlelemon.persistence

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class SharedPreferences(context: Context) {

    private val internalSharedPrefs = context.getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE)

    fun saveUser(user: User) {
        internalSharedPrefs.edit {
            with(user) {
                putString(FIRST_NAME_KEY, name)
                putString(LAST_NAME_KEY, lastName)
                putString(EMAIL, email)
            }
        }
    }

    fun getUser(): User? {
        with(internalSharedPrefs) {
            val name = getString(FIRST_NAME_KEY, null) ?: return null
            val lastName = getString(LAST_NAME_KEY, null) ?: return null
            val email = getString(EMAIL, null) ?: return null
            return User(name, lastName, email)
        }
    }

    fun isLoggedIn() = getUser() != null

    companion object {
        private const val SHARED_PREFS_KEY = "internal-shared-prefs"

        private const val FIRST_NAME_KEY = "firstName"
        private const val LAST_NAME_KEY = "lastName"
        private const val EMAIL = "email"
    }
}