package com.dicoding.picodiploma.loginwithanimation.data.api

import com.dicoding.picodiploma.loginwithanimation.data.response.AccountResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationRequest
import com.dicoding.picodiploma.loginwithanimation.data.response.CreateConversationResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.DetailStoryResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.MessageResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RefreshResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseItem
import com.dicoding.picodiploma.loginwithanimation.data.response.SendMessageRequest
import com.dicoding.picodiploma.loginwithanimation.data.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("api/v1/user/get")
    suspend fun getAllUsers(): List<ResponseItem>

    @GET("api/v1/user")
    suspend fun searchUsers(
        @Query("search") keyword: String
    ): List<ResponseItem>
    ////////////////////////////////////////////////////////////////////
    @GET("api/v1/conversation")
    suspend fun getAllConversations(): Response<List<ConversationResponse>>

    @POST("api/v1/conversation")
    suspend fun createConversation(
        @Body request: CreateConversationRequest
    ): Response<CreateConversationResponse>

    @POST("api/v1/message")
    suspend fun sendMessage(
        @Body request: SendMessageRequest
    ): Response<MessageResponse>

    @GET("api/v1/message/{conversationId}")
    suspend fun getMessages(
        @Path("conversationId") conversationId: String
    ): Response<List<MessageResponse>>
    ////////////////////////////////////////////////////////////////////
    @GET("stories")
    suspend fun getStories(): StoryResponse

    @GET("stories/{id}")
    suspend fun getDetailStories(@Path("id")id: String): DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AccountResponse
    ////////////////////////////////////////////////////////////////////
    @FormUrlEncoded
    @POST("api/v1/auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/v1/auth/refreshtoken")
    suspend fun refreshLogin(
        @Field("refresh_token") refreshToken: String
    ): RefreshResponse

    @FormUrlEncoded
    @POST("api/v1/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}