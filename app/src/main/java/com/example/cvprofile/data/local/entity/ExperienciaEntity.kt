package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "experiencia",
    foreignKeys = [ForeignKey(
        entity = PortafolioEntity::class,
        parentColumns = ["id"],
        childColumns = ["portafolioId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExperienciaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val empresa: String,
    val cargo: String,
    val fechaInicio: String,
    val fechaFin: String
)
