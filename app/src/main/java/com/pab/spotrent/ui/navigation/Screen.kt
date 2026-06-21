package com.pab.spotrent.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Detail : Screen("detail/{propertyId}") {
        fun createRoute(propertyId: Int) = "detail/$propertyId"
    }
    object History : Screen("history")
    object Profile : Screen("profile")
}
