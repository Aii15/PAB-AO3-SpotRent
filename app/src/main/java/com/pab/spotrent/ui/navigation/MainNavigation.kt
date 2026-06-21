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
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.History.route) {
            // Placeholder for History
        }
        composable(Screen.Profile.route) {
            // Placeholder for Profile
        }
    }
}
