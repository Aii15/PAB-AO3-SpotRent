package com.pab.spotrent.data.model

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val fullName: String,
    val profilePicture: Int? = null // Using local resource for dummy
)
