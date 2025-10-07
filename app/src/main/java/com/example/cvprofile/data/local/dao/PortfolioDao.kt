package com.example.cvprofile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cvprofile.data.local.entity.PortfolioEntity

@Dao
interface PortfolioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(portfolio: PortfolioEntity)

    @Query("SELECT * FROM portfolio")
    suspend fun getAll(): List<PortfolioEntity>

}