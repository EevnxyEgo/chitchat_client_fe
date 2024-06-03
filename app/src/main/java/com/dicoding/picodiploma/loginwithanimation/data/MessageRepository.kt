package com.dicoding.picodiploma.loginwithanimation.data

import android.util.Log
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.ConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationRequest
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.MessageResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseItem
import com.dicoding.picodiploma.loginwithanimation.data.response.SendMessageRequest
import kotlinx.coroutines.flow.Flow

class MessageRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getAllConversations(): List<ConversationResponse> {
        val response = apiService.getAllConversations()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch conversations: ${response.message()}")
        }
    }

    suspend fun createConversation(request: CreateConversationRequest): CreateConversationResponse? {
        val response = apiService.createConversation(request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw Exception("Failed to create conversation: ${response.message()}")
        }
    }


    suspend fun sendMessage(conversationId: String, message: String): MessageResponse? {
        val request = SendMessageRequest(conversationId, message)
        val response = apiService.sendMessage(request)
        return if (response.isSuccessful) {
            response.body()
        } else {
            throw Exception("Failed to send message: ${response.message()}")
        }
    }

    suspend fun getMessages(conversationId: String): List<MessageResponse> {
        val response = apiService.getMessages(conversationId)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            Log.d("JSON Response", "Response: ${responseBody.toString()}")
            responseBody ?: emptyList()
        } else {
            throw Exception("Failed to fetch messages: ${response.message()}")
        }
    }

    suspend fun searchUsers(keyword: String): List<ResponseItem> {
        return apiService.searchUsers(keyword)
    }

    suspend fun getAllUsers(): List<ResponseItem> {
        return apiService.getAllUsers()
    }

    companion object {
        @Volatile
        private var instance: MessageRepository? = null

        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = instance ?: synchronized(this) {
            instance ?: MessageRepository(userPreference, apiService).also { instance = it }
        }
    }
}