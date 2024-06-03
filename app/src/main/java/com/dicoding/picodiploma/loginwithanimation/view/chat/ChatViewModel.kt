package com.dicoding.picodiploma.loginwithanimation.view.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.MessageRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationRequest
import com.dicoding.picodiploma.loginwithanimation.data.response.MessageResponse
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: MessageRepository) : ViewModel() {

    private val _conversations = MutableLiveData<List<ConversationResponse>>()
    val conversations: LiveData<List<ConversationResponse>> get() = _conversations

    private val _newConversation = MutableLiveData<ConversationResponse?>()
    val newConversation: LiveData<ConversationResponse?> get() = _newConversation

    private val _messages = MutableLiveData<List<MessageResponse>>()
    val messages: LiveData<List<MessageResponse>> get() = _messages

    private val _sendMessageResponse = MutableLiveData<MessageResponse?>()
    val sendMessageResponse: LiveData<MessageResponse?> get() = _sendMessageResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getConversations() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val conversations = repository.getAllConversations()
                _conversations.postValue(conversations)
            } catch (e: Exception) {
                _error.value = "Failed to fetch conversations: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createConversation(receiverId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val request = CreateConversationRequest(receiverId)
                val response = repository.createConversation(request)
                if (response != null) {
                    val conversation = response.toConversationResponse()
                    _newConversation.postValue(conversation)
                    getConversations()
                } else {
                    _error.value = "Failed to create conversation: response is null"
                }
            } catch (e: Exception) {
                _error.value = "Failed to create conversation: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMessages(conversationId: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.getMessages(conversationId)
                _messages.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun sendMessage(conversationId: String, message: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.sendMessage(conversationId, message)
                if (response != null) {
                    _sendMessageResponse.postValue(response)
                    getMessages(conversationId)
                }else {
                    _error.value = "Failed to send message: response is null"
             }
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}
