package com.alejandrossm.actualizaciondsy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alejandrossm.actualizaciondsy.HomeScreen
import com.alejandrossm.actualizaciondsy.view.RegistroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToRegistro = { navController.navigate("registro") }
            )
        }
        composable("registro") {
            RegistroScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
