package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experience")
data class ExperienceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val companyName: String,
    val position: String,
    val startDate: String,
    val endDate: String?,
    val description: String?
)
