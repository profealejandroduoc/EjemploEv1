package com.alejandrossm.actualizaciondsy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.alejandrossm.actualizaciondsy.data.AppDatabase
import com.alejandrossm.actualizaciondsy.view.EdicionScreen
import com.alejandrossm.actualizaciondsy.view.HomeScreen
import com.alejandrossm.actualizaciondsy.view.RegistroScreen
import com.alejandrossm.actualizaciondsy.viewmodel.PersonaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    // Usamos remember para obtener la instancia solo una vez por ciclo de vida del Composable
    val database = remember { AppDatabase.getDatabase(context) }
    val personaDao = remember { database.personaDao() }

    val viewModel: PersonaViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return PersonaViewModel(personaDao) as T
            }
        }
    )

    NavHost(navController = navController, startDestination = Rutas.Home.route) {
        composable(Rutas.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToRegistro = {
                    viewModel.resetForm()
                    navController.navigate(Rutas.Registro.route)
                },
                onNavigateToEdicion = { rut ->
                    navController.navigate(Rutas.Edicion.createRoute(rut))
                }
            )
        }
        composable(Rutas.Registro.route) {
            RegistroScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Rutas.Edicion.route,
            arguments = listOf(navArgument("rut") { type = NavType.StringType })
        ) { backStackEntry ->
            val rut = backStackEntry.arguments?.getString("rut") ?: ""
            EdicionScreen(
                viewModel = viewModel,
                rutParam = rut,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
