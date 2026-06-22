package com.pab.spotrent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pab.spotrent.ui.screens.HomeScreen
import com.pab.spotrent.ui.screens.LoginScreen
import com.pab.spotrent.ui.screens.RegisterScreen
import com.pab.spotrent.ui.screens.PropertyDetailScreen
import com.pab.spotrent.ui.screens.BookingCalendarScreen
import com.pab.spotrent.ui.screens.PaymentMethodScreen
import com.pab.spotrent.ui.screens.PaymentConfirmationScreen
import com.pab.spotrent.ui.screens.ProfileScreen
import com.pab.spotrent.ui.screens.HistoryScreen
import com.pab.spotrent.ui.screens.BookingDetailScreen
import com.pab.spotrent.ui.screens.AccountDetailScreen
import com.pab.spotrent.ui.screens.ChangePasswordScreen
import com.pab.spotrent.ui.screens.AboutScreen
import com.pab.spotrent.data.repository.AuthRepository

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onPropertyClick = { propertyId ->
                    navController.navigate(Screen.Detail.createRoute(propertyId))
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                }
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 1
            PropertyDetailScreen(
                propertyId = propertyId,
                onBackClick = { navController.popBackStack() },
                onBookingClick = {
                    if (AuthRepository.isLoggedIn()) {
                        navController.navigate(Screen.BookingCalendar.createRoute(propertyId))
                    } else {
                        navController.navigate(Screen.Login.route)
                    }
                }
            )
        }
        composable(
            route = Screen.BookingCalendar.route,
            arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 1
            BookingCalendarScreen(
                propertyId = propertyId,
                onBackClick = { navController.popBackStack() },
                onNextClick = { startDate, endDate ->
                    navController.navigate(Screen.PaymentMethod.createRoute(propertyId, startDate, endDate))
                }
            )
        }
        composable(
            route = Screen.PaymentMethod.route,
            arguments = listOf(
                navArgument("propertyId") { type = NavType.IntType },
                navArgument("startDate") { type = NavType.LongType },
                navArgument("endDate") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 1
            val startDate = backStackEntry.arguments?.getLong("startDate") ?: 0L
            val endDate = backStackEntry.arguments?.getLong("endDate") ?: 0L
            PaymentMethodScreen(
                propertyId = propertyId,
                startDate = startDate,
                endDate = endDate,
                onBackClick = { navController.popBackStack() },
                onNextClick = { paymentMethod ->
                    navController.navigate(Screen.PaymentConfirmation.createRoute(propertyId, startDate, endDate, paymentMethod))
                }
            )
        }
        composable(
            route = Screen.PaymentConfirmation.route,
            arguments = listOf(
                navArgument("propertyId") { type = NavType.IntType },
                navArgument("startDate") { type = NavType.LongType },
                navArgument("endDate") { type = NavType.LongType },
                navArgument("paymentMethod") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 1
            val startDate = backStackEntry.arguments?.getLong("startDate") ?: 0L
            val endDate = backStackEntry.arguments?.getLong("endDate") ?: 0L
            val paymentMethod = backStackEntry.arguments?.getString("paymentMethod") ?: ""
            PaymentConfirmationScreen(
                propertyId = propertyId,
                startDate = startDate,
                endDate = endDate,
                paymentMethod = paymentMethod,
                onBackClick = { navController.popBackStack() },
                onChangeDateClick = {
                    navController.navigate(Screen.BookingCalendar.createRoute(propertyId)) {
                        popUpTo(Screen.BookingCalendar.route) { inclusive = true }
                    }
                },
                onPaymentSuccess = {
                    navController.navigate(Screen.History.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
        composable(Screen.History.route) {
            HistoryScreen(
                onBackClick = { navController.popBackStack() },
                onBookingClick = { bookingId ->
                    navController.navigate(Screen.BookingDetail.createRoute(bookingId))
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }
        composable(
            route = Screen.BookingDetail.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId") ?: ""
            BookingDetailScreen(
                bookingId = bookingId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                },
                onNavigateToAccountDetail = {
                    navController.navigate(Screen.AccountDetail.route)
                },
                onNavigateToChangePassword = {
                    navController.navigate(Screen.ChangePassword.route)
                },
                onNavigateToAbout = {
                    navController.navigate(Screen.About.route)
                },
                onLogoutSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.AccountDetail.route) {
            AccountDetailScreen(
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.ChangePassword.route) {
            ChangePasswordScreen(
                onBackClick = { navController.popBackStack() },
                onChangeSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.About.route) {
            AboutScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
