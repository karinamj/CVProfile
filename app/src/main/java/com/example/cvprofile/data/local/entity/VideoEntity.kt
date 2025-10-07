package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "video",
    foreignKeys = [ForeignKey(
        entity = PortafolioEntity::class,
        parentColumns = ["id"],
        childColumns = ["portafolioId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class VideoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val portafolioId: Int,
    val titulo: String,
    val url: String,
    val descripcion: String
)
