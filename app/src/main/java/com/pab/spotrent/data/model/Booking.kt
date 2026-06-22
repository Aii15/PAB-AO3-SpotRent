package com.pab.spotrent.data.model

data class Booking(
    val id: String,
    val propertyId: Int,
    val propertyName: String,
    val propertyLocation: String,
    val propertyThumbnail: Int,
    val startDate: Long,
    val endDate: Long,
    val totalPrice: Long,
    val status: String = "Berhasil",
    val paymentMethod: String
)
