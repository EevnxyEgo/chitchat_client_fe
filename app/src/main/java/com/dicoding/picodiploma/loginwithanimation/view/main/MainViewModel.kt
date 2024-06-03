package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

//    private val _storyResponse = MutableLiveData<List<ListStoryItem>>()
//    val storyResponse: LiveData<List<ListStoryItem>> get() = _storyResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSession = MutableLiveData<UserModel?>()
    val userSession: LiveData<UserModel?> get() = _userSession

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun getStory() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val response = repository.getStories()
//                if (response.error == true) {
//                    _error.value = response.message ?: "Unknown error"
//                } else {
//                    _isLoading.value = false
//                    _storyResponse.value = response.listStory
//                }
//            } catch (e: HttpException) {
//                _isLoading.value = false
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, StoryResponse::class.java)
//                _error.value = errorBody.message ?: "Network error"
//            } catch (e: Exception) {
//                _isLoading.value = false
//                _error.value = "Network error: ${e.message}"
//            }
//        }
//    }
}