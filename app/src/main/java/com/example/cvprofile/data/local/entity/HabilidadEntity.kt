package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "habilidad",
    foreignKeys = [ForeignKey(
        entity = PortafolioEntity::class,
        parentColumns = ["id"],
        childColumns = ["portafolioId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class HabilidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val portafolioId: Int,
    val nombre: String
)

