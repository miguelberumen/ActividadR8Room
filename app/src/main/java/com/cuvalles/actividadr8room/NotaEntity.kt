package com.cuvalles.actividadr8room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notas")
data class NotaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val contenido: String
)