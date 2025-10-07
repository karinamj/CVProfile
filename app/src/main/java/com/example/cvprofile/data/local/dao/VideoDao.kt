package com.example.cvprofile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cvprofile.data.local.entity.VideoEntity

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(video: VideoEntity)

    @Query("SELECT * FROM video LIMIT 1")
    suspend fun getVideo(): VideoEntity?

    @Update
    suspend fun update(video: VideoEntity)
}