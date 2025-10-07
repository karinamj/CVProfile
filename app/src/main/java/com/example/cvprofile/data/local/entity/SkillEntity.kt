package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill")
data class SkillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

