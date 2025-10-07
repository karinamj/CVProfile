package com.example.cvprofile.ui.edit.skill

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cvprofile.data.AppDatabase
import com.example.cvprofile.data.local.entity.SkillEntity
import kotlinx.coroutines.launch

class SkillViewModel(application: Application) : AndroidViewModel(application) {

    private val skillDao = AppDatabase.getDatabase(application).skillDao()

    val skills: LiveData<List<SkillEntity>> = skillDao.getAllSkills()

    fun addSkill(skillName: String) {
        viewModelScope.launch {
            val skill = SkillEntity(
                name = skillName
            )
            skillDao.insertSkill(skill)
        }
    }

    fun deleteSkill(skill: SkillEntity) {
        viewModelScope.launch {
            skillDao.deleteSkill(skill)
        }
    }
}
