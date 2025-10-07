package com.example.cvprofile.ui.edit.video

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cvprofile.data.AppDatabase
import com.example.cvprofile.data.local.entity.VideoEntity
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val videoDao = db.videoDao()

    private val _video = MutableLiveData<VideoEntity?>()
    val video: LiveData<VideoEntity?> get() = _video

    fun loadVideo() {
        viewModelScope.launch {
            val videoData = videoDao.getVideo()
            _video.postValue(videoData)
        }
    }

    fun insertVideo(video: VideoEntity) {
        viewModelScope.launch {
            videoDao.insert(video)
        }
    }
}
