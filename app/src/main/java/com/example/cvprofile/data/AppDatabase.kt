package com.example.cvprofile.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cvprofile.data.local.dao.ExperienceDao
import com.example.cvprofile.data.local.dao.PortfolioDao
import com.example.cvprofile.data.local.dao.SkillDao
import com.example.cvprofile.data.local.dao.UserDao
import com.example.cvprofile.data.local.dao.VideoDao
import com.example.cvprofile.data.local.entity.ExperienceEntity
import com.example.cvprofile.data.local.entity.PortfolioEntity
import com.example.cvprofile.data.local.entity.SkillEntity
import com.example.cvprofile.data.local.entity.UserEntity
import com.example.cvprofile.data.local.entity.VideoEntity

@Database(entities = [
    UserEntity::class,
    PortfolioEntity::class,
    SkillEntity::class,
    ExperienceEntity::class,
    VideoEntity::class],
    version = 14,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun portfolioDao(): PortfolioDao
    abstract fun skillDao(): SkillDao
    abstract fun experienceDao(): ExperienceDao
    abstract fun videoDao(): VideoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cvprofile_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}