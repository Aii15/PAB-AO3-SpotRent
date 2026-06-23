package com.pab.spotrent.data.model

data class Review(
    val id: String,
    val propertyId: Int,
    val bookingId: String?,
    val userName: String,
    val userAvatarText: String,
    val date: String,
    val rating: Int,
    val comment: String
)
