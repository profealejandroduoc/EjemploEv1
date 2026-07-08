package com.alejandrossm.actualizaciondsy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandrossm.actualizaciondsy.model.Persona
import com.alejandrossm.actualizaciondsy.data.PersonaDao
import com.alejandrossm.actualizaciondsy.util.esRutValido
import com.alejandrossm.actualizaciondsy.util.formatearRut
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonaViewModel(private val personaDao: PersonaDao) : ViewModel() {

    val personas: StateFlow<List<Persona>> = personaDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _rut = MutableStateFlow("")
    val rut: StateFlow<String> = _rut.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _apellido = MutableStateFlow("")
    val apellido: StateFlow<String> = _apellido.asStateFlow()

    private val _edad = MutableStateFlow("")
    val edad: StateFlow<String> = _edad.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled.asStateFlow()

    private val _rutError = MutableStateFlow<String?>(null)
    val rutError: StateFlow<String?> = _rutError.asStateFlow()

    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _apellidoError = MutableStateFlow<String?>(null)
    val apellidoError: StateFlow<String?> = _apellidoError.asStateFlow()

    private val _edadError = MutableStateFlow<String?>(null)
    val edadError: StateFlow<String?> = _edadError.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast.asStateFlow()

    fun onRutChange(newValue: String) {
        _rut.value = newValue
        viewModelScope.launch {
            val exists = personaDao.getByRut(newValue) != null
            _rutError.value = when {
                newValue.isBlank() -> "El RUT es obligatorio"
                !newValue.esRutValido() -> "El RUT no es válido"
                exists -> "Este RUT ya está registrado"
                else -> null
            }
            validate()
        }
    }

    fun onRutFocusLost() {
        val current = _rut.value
        if (current.isNotBlank()) {
            val formatted = current.formatearRut()
            // Si el valor cambia al formatear, lo actualizamos y re-validamos
            if (formatted != current) {
                onRutChange(formatted)
            }
        }
    }

    fun onNombreChange(newValue: String) {
        _nombre.value = newValue
        _nombreError.value = if (newValue.isBlank()) "El nombre es obligatorio" else null
        validate()
    }

    fun onApellidoChange(newValue: String) {
        _apellido.value = newValue
        _apellidoError.value = if (newValue.isBlank()) "El apellido es obligatorio" else null
        validate()
    }

    fun onEdadChange(newValue: String) {
        if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
            _edad.value = newValue
            val edadInt = newValue.toIntOrNull() ?: 0
            _edadError.value = when {
                newValue.isBlank() -> "La edad es obligatoria"
                edadInt <= 0 -> "La edad debe ser mayor a 0"
                else -> null
            }
            validate()
        }
    }

    private fun validate() {
        val edadInt = _edad.value.toIntOrNull() ?: 0
        _isButtonEnabled.value = _rut.value.isNotBlank() &&
                _rutError.value == null &&
                _nombre.value.isNotBlank() &&
                _nombreError.value == null &&
                _apellido.value.isNotBlank() &&
                _apellidoError.value == null &&
                _edad.value.isNotBlank() &&
                _edadError.value == null &&
                edadInt > 0
    }

    fun registrarPersona(onSuccess: () -> Unit) {
        if (_isButtonEnabled.value) {
            viewModelScope.launch {
                val persona = Persona(
                    rut = _rut.value,
                    nombre = _nombre.value,
                    apellido = _apellido.value,
                    edad = _edad.value.toInt()
                )
                personaDao.insert(persona)
                _showToast.value = true
                kotlinx.coroutines.delay(2000)
                _showToast.value = false
                resetForm()
                onSuccess()
            }
        }
    }

    fun cargarPersona(rut: String) {
        viewModelScope.launch {
            personaDao.getByRut(rut)?.let { persona ->
                _rut.value = persona.rut
                _nombre.value = persona.nombre
                _apellido.value = persona.apellido
                _edad.value = persona.edad.toString()
                
                // Limpiar errores al cargar
                _rutError.value = null
                _nombreError.value = null
                _apellidoError.value = null
                _edadError.value = null

                validate()
            }
        }
    }

    fun actualizarPersona(onSuccess: () -> Unit) {
        if (_isButtonEnabled.value) {
            viewModelScope.launch {
                val persona = Persona(
                    rut = _rut.value,
                    nombre = _nombre.value,
                    apellido = _apellido.value,
                    edad = _edad.value.toInt()
                )
                personaDao.update(persona)
                _showToast.value = true
                kotlinx.coroutines.delay(2000)
                _showToast.value = false
                resetForm()
                onSuccess()
            }
        }
    }

    fun resetForm() {
        _rut.value = ""
        _nombre.value = ""
        _apellido.value = ""
        _edad.value = ""
        _rutError.value = null
        _nombreError.value = null
        _apellidoError.value = null
        _edadError.value = null
        _isButtonEnabled.value = false
    }

    fun eliminarPersona(persona: Persona) {
        viewModelScope.launch {
            personaDao.delete(persona)
        }
    }
}
