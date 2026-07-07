package com.alejandrossm.actualizaciondsy.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonaDao {
    @Query("SELECT * FROM personas")
    fun getAll(): Flow<List<Persona>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(persona: Persona)

    @Update
    suspend fun update(persona: Persona)

    @Delete
    suspend fun delete(persona: Persona)

    @Query("SELECT * FROM personas WHERE rut = :rut LIMIT 1")
    suspend fun getByRut(rut: String): Persona?
}
