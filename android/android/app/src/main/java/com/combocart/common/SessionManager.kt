package com.combocart.common

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("combocart_prefs", Context.MODE_PRIVATE)

    data class UserCredentials(val phone: String, val password: String)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_LOCATION = "user_location"
        const val USER_PHONE = "user_phone"
        const val USER_PASSWORD = "user_password"
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveLocation(address: String) {
        val editor = prefs.edit()
        editor.putString(USER_LOCATION, address)
        editor.apply()
    }

    fun fetchLocation(): String {
        return prefs.getString(USER_LOCATION, "Koramangala, Bangalore") ?: "Koramangala, Bangalore"
    }

    /**
     * Store users as a simple map of phone to password in a single string for mock purposes
     */
    private fun getAllUsers(): MutableMap<String, String> {
        val usersString = prefs.getString("all_users", null) ?: return mutableMapOf()
        // Simple manual parsing for mock
        val map = mutableMapOf<String, String>()
        usersString.split(";").filter { it.contains(":") }.forEach {
            val parts = it.split(":")
            if (parts.size == 2) map[parts[0]] = parts[1]
        }
        return map
    }

    private fun saveAllUsers(users: Map<String, String>) {
        val usersString = users.entries.joinToString(";") { "${it.key}:${it.value}" }
        prefs.edit().putString("all_users", usersString).apply()
    }

    fun saveUserCredentials(phone: String, password: String) {
        val users = getAllUsers()
        users[phone] = password
        saveAllUsers(users)
        
        // Also keep track of "current" or "last" user for convenience
        val editor = prefs.edit()
        editor.putString(USER_PHONE, phone)
        editor.putString(USER_PASSWORD, password)
        editor.apply()
    }

    fun verifyCredentials(phone: String, password: String): Boolean {
        val users = getAllUsers()
        return users[phone] == password
    }

    fun isUserRegistered(phone: String): Boolean {
        val users = getAllUsers()
        return users.containsKey(phone)
    }

    fun clearData() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}
