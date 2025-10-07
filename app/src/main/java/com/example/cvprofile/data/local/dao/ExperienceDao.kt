package com.example.cvprofile.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cvprofile.data.local.entity.ExperienceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExperienceDao {

    @Query("SELECT * FROM experience ORDER BY id DESC")
    fun getAllExperiences(): LiveData<List<ExperienceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: ExperienceEntity)

    @Delete
    suspend fun deleteExperience(experience: ExperienceEntity)
}