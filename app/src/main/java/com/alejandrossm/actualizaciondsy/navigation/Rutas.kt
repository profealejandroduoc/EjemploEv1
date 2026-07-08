package com.alejandrossm.actualizaciondsy.navigation

sealed class Rutas(val route: String) {
    data object Home : Rutas("home")
    data object Registro : Rutas("registro")
    data object Edicion : Rutas("edicion/{rut}") {
        fun createRoute(rut: String) = "edicion/$rut"
    }
}
