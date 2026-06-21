package com.pab.spotrent.data.model

data class Property(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val price: Long,
    val rating: Double,
    val reviews: Int,
    val thumbnailRes: Int, // Resource ID for Home Screen card
    val detailImages: List<Int>, // List of resource IDs for Detail Screen pager
    val type: String,
    val specifications: List<String> = emptyList()
)
