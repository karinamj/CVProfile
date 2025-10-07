package com.example.cvprofile.ui.edit.experience

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cvprofile.data.AppDatabase
import com.example.cvprofile.data.local.entity.ExperienceEntity
import kotlinx.coroutines.launch

class ExperienceViewModel(application: Application) : AndroidViewModel(application) {

    private val experienceDao = AppDatabase.getDatabase(application).experienceDao()
    val experiences: LiveData<List<ExperienceEntity>> = experienceDao.getAllExperiences()

    fun addExperience(
        company: String,
        position: String,
        start: String,
        end: String?,
        desc: String?
    ) {
        viewModelScope.launch {
            val experience = ExperienceEntity(
                companyName = company,
                position = position,
                startDate = start,
                endDate = end,
                description = desc
            )
            experienceDao.insertExperience(experience)
        }
    }

    fun deleteExperience(experience: ExperienceEntity) {
        viewModelScope.launch {
            experienceDao.deleteExperience(experience)
        }
    }
}