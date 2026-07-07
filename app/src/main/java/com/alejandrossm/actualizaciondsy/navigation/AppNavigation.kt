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

    NavHost(navController = navController, startDestination = Rutas.Home.route) {
        composable(Rutas.Home.route) {
            HomeScreen(
                onNavigateToRegistro = { navController.navigate(Rutas.Registro.route) }
            )
        }
        composable(Rutas.Registro.route) {
            RegistroScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
