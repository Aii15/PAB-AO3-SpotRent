package com.pab.spotrent.data.repository

import com.pab.spotrent.R
import com.pab.spotrent.data.model.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.UUID

object BookingRepository {
    private val dummyStart = Calendar.getInstance().apply {
        set(2026, Calendar.JULY, 2, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private val dummyEnd = Calendar.getInstance().apply {
        set(2026, Calendar.JULY, 4, 0, 0, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    private val _bookings = MutableStateFlow<List<Booking>>(listOf(
        Booking(
            id = "BKG001",
            propertyId = 1,
            propertyName = "Kota Tua Jakarta",
            propertyLocation = "Jakarta Barat",
            propertyThumbnail = R.drawable.prop_default,
            startDate = dummyStart,
            endDate = dummyEnd,
            totalPrice = 45000000L,
            status = "Berhasil",
            paymentMethod = "Transfer Bank"
        )
    ))
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
