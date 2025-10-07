package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val availability: String?,
    val linkedin: String?,
    val github: String?,
    val website: String?,
    val resumePath: String?,
    val profession: String?,
    val description: String?,
    val profileImagePath: String?
)
