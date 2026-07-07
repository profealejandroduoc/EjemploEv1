package com.alejandrossm.actualizaciondsy.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandrossm.actualizaciondsy.viewmodel.PersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: PersonaViewModel, onNavigateToRegistro: () -> Unit) {
    val personas by viewModel.personas.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Registro de Personas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Listado de Personas",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                FloatingActionButton(
                    onClick = onNavigateToRegistro,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Persona")
                }
            }

            // Listado de personas
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(personas) { persona ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = persona.rut,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = "${persona.nombre} ${persona.apellido}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Edad: ${persona.edad}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
