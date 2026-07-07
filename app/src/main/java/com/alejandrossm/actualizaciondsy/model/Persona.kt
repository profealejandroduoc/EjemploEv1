package com.alejandrossm.actualizaciondsy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personas")
data class Persona(
    @PrimaryKey
    val rut: String,
    val nombre: String,
    val apellido: String,
    val edad: Int
)
