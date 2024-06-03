package com.dicoding.picodiploma.loginwithanimation.view.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.MessageRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseItem
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: MessageRepository) : ViewModel() {

    private val _contactResponse = MutableLiveData<List<ResponseItem>>()
    val contactResponse: LiveData<List<ResponseItem>> get() = _contactResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSession = MutableLiveData<UserModel?>()
    val userSession: LiveData<UserModel?> get() = _userSession

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun searchUsers(keyword: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val users = repository.searchUsers(keyword)
                _contactResponse.value = users
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Failed to search users: ${e.message}"
            }
        }
    }

    fun getAllUsers() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val users = repository.getAllUsers()
                _contactResponse.value = users
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = "Failed to get all users: ${e.message}"
            }
        }
    }
}
