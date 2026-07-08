package com.alejandrossm.actualizaciondsy.viewmodel

import com.alejandrossm.actualizaciondsy.data.PersonaDao
import com.alejandrossm.actualizaciondsy.model.Persona
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonaViewModelTest {

    private lateinit var viewModel: PersonaViewModel
    private val personaDao = mockk<PersonaDao>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { personaDao.getAll() } returns flowOf(emptyList())
        viewModel = PersonaViewModel(personaDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `registrar Persona con datos validos`() {

        val rutValido = "12345678-5" 
        val nombre = "Juan"
        val apellido = "Perez"
        val edad = "25"

        viewModel.onRutChange(rutValido)
        viewModel.onNombreChange(nombre)
        viewModel.onApellidoChange(apellido)
        viewModel.onEdadChange(edad)


        viewModel.registrarPersona { }
        
        coVerify { 
            personaDao.insert(
                Persona(
                    rut = rutValido,
                    nombre = nombre,
                    apellido = apellido,
                    edad = 25
                )
            ) 
        }
    }

    @Test
    fun `listar personas`() {

        val listaSimulada = listOf(
            Persona("11111111-1", "Persona 1", "Uno", 20),
            Persona("22222222-2", "Persona 2", "Dos", 30)
        )
        every { personaDao.getAll() } returns flowOf(listaSimulada)

        viewModel = PersonaViewModel(personaDao)
        val personasEnViewModel = viewModel.personas.value

        assertEquals("La lista debería tener 2 personas", 2, personasEnViewModel.size)
        assertEquals("El primer nombre debería coincidir", "Persona 1", personasEnViewModel[0].nombre)
        assertEquals("El segundo RUT debería coincidir", "22222222-2", personasEnViewModel[1].rut)
    }

    @Test
    fun `actualizar Persona`() {

        val rutExistente = "12345678-5"
        val personaOriginal = Persona(rutExistente, "Nombre Antiguo", "Apellido Antiguo", 40)
        coEvery { personaDao.getByRut(rutExistente) } returns personaOriginal
        
        viewModel.cargarPersona(rutExistente)
        val nuevoNombre = "Juan"
        val nuevoApellido = "Soto"
        val nuevaEdad = "28"

        viewModel.onNombreChange(nuevoNombre)
        viewModel.onApellidoChange(nuevoApellido)
        viewModel.onEdadChange(nuevaEdad)
        viewModel.actualizarPersona { }

        coVerify { 
            personaDao.update(
                Persona(
                    rut = rutExistente,
                    nombre = nuevoNombre,
                    apellido = nuevoApellido,
                    edad = 28
                )
            ) 
        }
    }
}
