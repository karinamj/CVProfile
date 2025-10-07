package com.example.cvprofile.ui.edit.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cvprofile.data.AppDatabase
import com.example.cvprofile.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class UserEditViewModel(application: Application) : AndroidViewModel(application)  {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    fun getUser(): LiveData<UserEntity?> {
        return liveData {
            emit(userDao.getUser())
        }
    }
}