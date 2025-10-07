package com.example.cvprofile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val url: String?,
    val description: String?
)
