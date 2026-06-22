package com.pab.spotrent.data.repository

import com.pab.spotrent.R
import com.pab.spotrent.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthRepository {
    // Dummy users
    private val dummyUsers = listOf(
        User(1, "admin", "admin@spotrent.com", "Admin SpotRent", R.drawable.ic_profile),
        User(2, "user", "user@gmail.com", "John Doe", R.drawable.ic_profile)
    )

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(identifier: String, password: String): Boolean {
        // Simple logic for dummy backend
        // In a real backend, this would be a network call
        val user = dummyUsers.find { (it.username == identifier || it.email == identifier) && password == "password123" }
        return if (user != null) {
            _currentUser.value = user
            true
        } else {
            false
        }
    }

    fun logout() {
        _currentUser.value = null
    }

    fun isLoggedIn(): Boolean = _currentUser.value != null
}
