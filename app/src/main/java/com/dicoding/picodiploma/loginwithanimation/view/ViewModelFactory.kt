package com.dicoding.picodiploma.loginwithanimation.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.di.Injection
import com.dicoding.picodiploma.loginwithanimation.view.addstory.AddStoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.detailstory.DetailStoryViewModel
import com.dicoding.picodiploma.loginwithanimation.view.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: StoryRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailStoryViewModel::class.java) -> {
                DetailStoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
//            modelClass.isAssignableFrom(DetailProfileViewModel::class.java) -> {
//                DetailProfileViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(ContactViewModel::class.java) -> {
//                ContactViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(ChatViewModel::class.java) -> {
//                ChatViewModel(repository) as T
//            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) =
            ViewModelFactory(Injection.provideStoryRepository(context))
    }
}