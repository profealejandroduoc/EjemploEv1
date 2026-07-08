package com.alejandrossm.actualizaciondsy.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alejandrossm.actualizaciondsy.view.components.SuccessToast
import com.alejandrossm.actualizaciondsy.viewmodel.PersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(viewModel: PersonaViewModel, onBack: () -> Unit) {
    val rut by viewModel.rut.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val apellido by viewModel.apellido.collectAsState()
    val edad by viewModel.edad.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()
    val showToast by viewModel.showToast.collectAsState()

    val rutError by viewModel.rutError.collectAsState()
    val nombreError by viewModel.nombreError.collectAsState()
    val apellidoError by viewModel.apellidoError.collectAsState()
    val edadError by viewModel.edadError.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Registro de Personas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Registrar Persona",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = rut,
                onValueChange = { viewModel.onRutChange(it) },
                label = { Text("RUT") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused) {
                            viewModel.onRutFocusLost()
                        }
                    },
                isError = rutError != null,
                supportingText = { rutError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                isError = nombreError != null,
                supportingText = { nombreError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = apellido,
                onValueChange = { viewModel.onApellidoChange(it) },
                label = { Text("Apellido") },
                modifier = Modifier.fillMaxWidth(),
                isError = apellidoError != null,
                supportingText = { apellidoError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = edad,
                onValueChange = { viewModel.onEdadChange(it) },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = edadError != null,
                supportingText = { edadError?.let { Text(it) } }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.registrarPersona {
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text("Registrar")
            }
        }

        SuccessToast(
            show = showToast,
            message = "Persona registrada con exito!!!",
            modifier = Modifier.padding(innerPadding)
        )
    }
}
