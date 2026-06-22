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
    object AccountDetail : Screen("account_detail")
    object ChangePassword : Screen("change_password")
    object About : Screen("about")
    object Wishlist : Screen("wishlist")
    
    // Booking screens
    object BookingCalendar : Screen("booking_calendar/{propertyId}") {
        fun createRoute(propertyId: Int) = "booking_calendar/$propertyId"
    }
    object PaymentMethod : Screen("payment_method/{propertyId}/{startDate}/{endDate}") {
        fun createRoute(propertyId: Int, startDate: Long, endDate: Long) = 
            "payment_method/$propertyId/$startDate/$endDate"
    }
    object PaymentConfirmation : Screen("payment_confirmation/{propertyId}/{startDate}/{endDate}/{paymentMethod}") {
        fun createRoute(propertyId: Int, startDate: Long, endDate: Long, paymentMethod: String) = 
            "payment_confirmation/$propertyId/$startDate/$endDate/$paymentMethod"
    }
    object BookingDetail : Screen("booking_detail/{bookingId}") {
        fun createRoute(bookingId: String) = "booking_detail/$bookingId"
    }
}
