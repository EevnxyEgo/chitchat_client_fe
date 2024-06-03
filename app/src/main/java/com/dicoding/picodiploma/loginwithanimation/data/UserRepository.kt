package com.dicoding.picodiploma.loginwithanimation.data

import android.util.Log
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RefreshResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }
    suspend fun logout() {
        userPreference.logout()
    }
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }
    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }
    suspend fun refreshLogin(refreshToken: String): RefreshResponse {
        return apiService.refreshLogin(refreshToken)
    }

    suspend fun performApiCallWithTokenRefresh(apiCall: suspend (String) -> Unit) {
        try {
            val user = getSession().first()
            val accessToken = user.accessToken
            try {
                apiCall(accessToken)
            } catch (e: HttpException) {
                if (e.code() == 401) { // Token expired
                    val refreshResponse = refreshLogin(user.refreshToken)
                    val newToken = refreshResponse.user?.token ?: throw Exception("Failed to refresh token")
                    val newUser = user.copy(accessToken = newToken)
                    saveSession(newUser)
                    apiCall(newToken) // Retry with new token
                } else {
                    throw e
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error performing API call: ${e.message}")
            throw e
        }
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}