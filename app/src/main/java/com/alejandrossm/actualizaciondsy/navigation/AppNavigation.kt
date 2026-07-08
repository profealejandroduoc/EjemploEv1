package com.alejandrossm.actualizaciondsy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alejandrossm.actualizaciondsy.data.AppDatabase
import com.alejandrossm.actualizaciondsy.view.HomeScreen
import com.alejandrossm.actualizaciondsy.view.RegistroScreen
import com.alejandrossm.actualizaciondsy.viewmodel.PersonaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val personaDao = database.personaDao()

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
                }
            )
        }
        composable(Rutas.Registro.route) {
            RegistroScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
