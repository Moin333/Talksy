package com.example.talksy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.talksy.data.FirebaseRepository

class RegisterViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    fun register(email: String, password: String, onResult: (Boolean) -> Unit) {
        repository.register(email, password, onResult)
    }
}