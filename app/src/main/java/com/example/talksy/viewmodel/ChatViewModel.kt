package com.example.talksy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.talksy.data.FirebaseRepository
import com.example.talksy.data.Message

class ChatViewModel : ViewModel() {
    private val repository = FirebaseRepository()

    fun sendMessage(chatId: String, message: Message) {
        repository.sendMessage(chatId, message)
    }
}