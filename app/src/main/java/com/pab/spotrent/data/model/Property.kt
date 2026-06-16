package com.pab.spotrent.data.model

data class Property(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val price: Long,
    val rating: Double,
    val reviews: Int,
    val imageUrl: String,
    val type: String,
    val specifications: List<String> = emptyList()
)
