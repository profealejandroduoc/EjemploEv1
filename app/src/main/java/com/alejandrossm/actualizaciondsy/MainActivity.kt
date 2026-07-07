package com.alejandrossm.actualizaciondsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alejandrossm.actualizaciondsy.navigation.AppNavigation
import com.alejandrossm.actualizaciondsy.ui.theme.ActualizacionDsyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActualizacionDsyTheme {
                AppNavigation()
            }
        }
    }
}
