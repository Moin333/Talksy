package com.example.talksy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.talksy.data.FirebaseRepository

class LoginViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        repository.login(email, password, onResult)
    }

    fun checkUserProfile(onResult: (Boolean) -> Unit) {
        repository.checkUserProfile(onResult)
    }
}