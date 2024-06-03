package com.dicoding.picodiploma.loginwithanimation.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.google.gson.annotations.SerializedName

@Parcelize
data class ConversationResponse(
	@SerializedName("_id")
	val id: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("picture")
	val picture: String,
	@SerializedName("isGroup")
	val isGroup: Boolean,
	@SerializedName("users")
	val users: List<ConversationUser>,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int,
	@SerializedName("latestMessage")
	val latestMessage: LatestMessage?
) : Parcelable

@Parcelize
data class ConversationUser(
	@SerializedName("_id")
	val id: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("email")
	val email: String,
	@SerializedName("picture")
	val picture: String,
	@SerializedName("status")
	val status: String,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int
) : Parcelable

@Parcelize
data class LatestMessage(
	@SerializedName("_id")
	val id: String,
	@SerializedName("sender")
	val sender: MessageSender,
	@SerializedName("message")
	val message: String,
	@SerializedName("conversation")
	val conversation: String,
//	@SerializedName("files")
//	val files: List<Any>,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int
) : Parcelable

@Parcelize
data class MessageSender(
	@SerializedName("_id")
	val id: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("email")
	val email: String,
	@SerializedName("picture")
	val picture: String,
	@SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class CreateConversationRequest(
	@SerializedName("receiverId")
	val receiverId: String
) : Parcelable

@Parcelize
data class CreateConversationResponse(
	@SerializedName("_id")
	val id: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("picture")
	val picture: String,
	@SerializedName("isGroup")
	val isGroup: Boolean,
	@SerializedName("users")
	val users: List<ConversationUser>,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int,
	@SerializedName("latestMessage")
	val latestMessage: CreateLatestMessage?
) : Parcelable

@Parcelize
data class CreateLatestMessage(
	@SerializedName("_id")
	val id: String,
	@SerializedName("sender")
	val sender: MessageSender,
	@SerializedName("message")
	val message: String,
	@SerializedName("conversation")
	val conversation: String,
//	@SerializedName("files")
//	val files: List<Any>,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int
) : Parcelable











