package com.dicoding.picodiploma.loginwithanimation.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.MessageRepository
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.chat.ChatViewModel
import com.dicoding.picodiploma.loginwithanimation.view.contact.ContactViewModel

class MessageViewModelFactory(private val repository: MessageRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
                ChatViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ContactViewModel::class.java) -> {
                ContactViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("unknown ViewModel Class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context)= MessageViewModelFactory(Injection.provideMessageRepository(context))
    }
}