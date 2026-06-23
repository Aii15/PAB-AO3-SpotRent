package com.pab.spotrent.data.repository

import android.content.Context
import com.pab.spotrent.data.database.UserDatabaseHelper
import com.pab.spotrent.data.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object BookingRepository {
    private var dbHelper: UserDatabaseHelper? = null

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun initialize(context: Context) {
        dbHelper = UserDatabaseHelper(context.applicationContext)
        refreshBookings()
    }

    fun refreshBookings() {
        val helper = dbHelper ?: return
        val currentUser = AuthRepository.currentUser.value
        if (currentUser != null) {
            val list = helper.getUserBookings(currentUser.id)
            _bookings.value = list
        } else {
            _bookings.value = emptyList()
        }
    }

    fun addBooking(booking: Booking) {
        val helper = dbHelper ?: return
        val currentUser = AuthRepository.currentUser.value ?: return
        val success = helper.addBooking(currentUser.id, booking)
        if (success) {
            refreshBookings()
        }
    }

    fun generateBookingId(): String = UUID.randomUUID().toString().take(8).uppercase()

    fun getBookingById(id: String): Booking? {
        return _bookings.value.find { it.id == id }
    }
}
