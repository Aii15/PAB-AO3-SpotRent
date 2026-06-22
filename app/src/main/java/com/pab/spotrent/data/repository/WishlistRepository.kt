package com.pab.spotrent.data.repository

import android.content.Context
import com.pab.spotrent.data.database.UserDatabaseHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object WishlistRepository {
    private var dbHelper: UserDatabaseHelper? = null

    private val _wishlistedIds = MutableStateFlow<Set<Int>>(emptySet())
    val wishlistedIds: StateFlow<Set<Int>> = _wishlistedIds.asStateFlow()

    fun initialize(context: Context) {
        dbHelper = UserDatabaseHelper(context.applicationContext)
        refreshWishlist()
    }

    fun refreshWishlist() {
        val helper = dbHelper ?: return
        val currentUser = AuthRepository.currentUser.value
        if (currentUser != null) {
            val list = helper.getUserWishlist(currentUser.id)
            _wishlistedIds.value = list.toSet()
        } else {
            _wishlistedIds.value = emptySet()
        }
    }

    fun toggleWishlist(propertyId: Int): Boolean {
        val helper = dbHelper ?: return false
        val currentUser = AuthRepository.currentUser.value ?: return false
        val isCurrentlyWishlisted = _wishlistedIds.value.contains(propertyId)

        val success = if (isCurrentlyWishlisted) {
            helper.removeFromWishlist(currentUser.id, propertyId)
        } else {
            helper.addToWishlist(currentUser.id, propertyId)
        }

        if (success) {
            val currentSet = _wishlistedIds.value.toMutableSet()
            if (isCurrentlyWishlisted) {
                currentSet.remove(propertyId)
            } else {
                currentSet.add(propertyId)
            }
            _wishlistedIds.value = currentSet
        }
        return success
    }
}
