package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio")
data class PortfolioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val title: String,
    val description: String
)
