package com.cuvalles.actividadr8room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Query("SELECT * FROM notas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<NotaEntity>>

    @Insert
    suspend fun insertar(nota: NotaEntity)

    @Delete
    suspend fun eliminar(nota: NotaEntity)
}