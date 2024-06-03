package com.dicoding.picodiploma.loginwithanimation.view.detailstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailStoryViewModel(private val repository: StoryRepository) : ViewModel() {

//    private val _detailStoryResponse = MutableLiveData<Story>()
//    val detailStoryResponse: LiveData<Story> get() = _detailStoryResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

//    fun getDetailStories(id: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                _isLoading.value = false
//                val response = repository.getDetailStories(id)
//                if (response.error!!) {
//                    _error.value = response.message!!
//                    Log.e("DetailStoryViewModel", "Register error: ${response.message}")
//                } else {
//                    response.story?.let {
//                        _detailStoryResponse.value = it
//                    } ?: run {
//                        _error.value = "Story not found"
//                    }
//                }
//            } catch (e: HttpException) {
//                _isLoading.value = false
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, StoryResponse::class.java)
//                val errorMessage = errorBody.message
//                _error.value = errorMessage!!
//                Log.e("DetailStoryViewModel", "Network error: $errorMessage")
//            } catch (e: Exception) {
//                _isLoading.value = false
//                _error.value = "Network error: ${e.message}"
//                Log.e("DetailStoryViewModel", "Network error: ${e.message}")
//            }
//        }
//    }
}