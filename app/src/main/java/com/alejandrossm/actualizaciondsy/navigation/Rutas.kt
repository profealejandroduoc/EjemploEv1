package com.alejandrossm.actualizaciondsy.navigation

sealed class Rutas(val route: String) {
    data object Home : Rutas("home")
    data object Registro : Rutas("registro")
}
