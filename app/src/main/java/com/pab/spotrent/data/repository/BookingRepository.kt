package com.pab.spotrent.data.repository

import com.pab.spotrent.data.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object BookingRepository {
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()

    fun addBooking(booking: Booking) {
        val currentList = _bookings.value.toMutableList()
        currentList.add(0, booking) // Add to the top
        _bookings.value = currentList
    }

    fun generateBookingId(): String = UUID.randomUUID().toString().take(8).uppercase()

    fun getBookingById(id: String): Booking? {
        return _bookings.value.find { it.id == id }
    }
}
