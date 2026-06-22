package com.pab.spotrent.data.repository

import android.content.Context
import com.pab.spotrent.data.database.UserDatabaseHelper
import com.pab.spotrent.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthRepository {
    private var dbHelper: UserDatabaseHelper? = null

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun initialize(context: Context) {
        dbHelper = UserDatabaseHelper(context.applicationContext)
    }

    fun login(identifier: String, password: String): Boolean {
        val helper = dbHelper ?: return false
        val credentials = helper.getUserCredentials(identifier)
        if (credentials != null && credentials.second == password) {
            _currentUser.value = credentials.first
            return true
        }
        return false
    }

    fun register(username: String, email: String, fullName: String, password: String): Boolean {
        val helper = dbHelper ?: return false
        return helper.registerUser(username, email, fullName, password)
    }

    fun updateEmail(newEmail: String): Boolean {
        val user = _currentUser.value ?: return false
        val helper = dbHelper ?: return false
        val success = helper.updateEmail(user.id, newEmail)
        if (success) {
            _currentUser.value = user.copy(email = newEmail)
        }
        return success
    }

    fun updatePassword(newPassword: String): Boolean {
        val user = _currentUser.value ?: return false
        val helper = dbHelper ?: return false
        return helper.updatePassword(user.id, newPassword)
    }

    fun logout() {
        _currentUser.value = null
    }

    fun isLoggedIn(): Boolean = _currentUser.value != null
}
