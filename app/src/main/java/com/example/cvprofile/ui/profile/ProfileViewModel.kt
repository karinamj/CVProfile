package com.example.cvprofile.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cvprofile.data.AppDatabase
import com.example.cvprofile.data.local.entity.ExperienceEntity
import com.example.cvprofile.data.local.entity.SkillEntity
import com.example.cvprofile.data.local.entity.UserEntity
import kotlinx.coroutines.launch


class ProfileViewModel(application: Application) : AndroidViewModel(application){

    private val db = AppDatabase.getDatabase(application)
    private val userDao = db.userDao()
    private val skillDao = db.skillDao()
    private val experienceDao = db.experienceDao()

    val skills: LiveData<List<SkillEntity>> = skillDao.getAllSkills()
    val experiences: LiveData<List<ExperienceEntity>> = experienceDao.getAllExperiences()

    private val _user = MutableLiveData<UserEntity?>()
    val user: LiveData<UserEntity?> get() = _user

    fun loadUser() {
        viewModelScope.launch {
            val userData = userDao.getUser()
            _user.postValue(userData)
        }
    }


}
