package com.example.talksy.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.talksy.data.FirebaseRepository
import com.example.talksy.data.User

class ProfileViewModel : ViewModel() {
    private val repository = FirebaseRepository()


    fun saveUserProfile(username: String, bitmap: Bitmap, onResult: (Boolean) -> Unit) {
        repository.saveUserProfile(username, bitmap, onResult)
    }

    fun getUserProfile(onResult: (User?) -> Unit) {
        repository.getUserProfile(onResult)
    }
}