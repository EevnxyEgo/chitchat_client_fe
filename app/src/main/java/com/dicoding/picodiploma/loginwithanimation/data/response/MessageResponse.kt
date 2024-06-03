package com.dicoding.picodiploma.loginwithanimation.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MessageResponse(
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("sender")
	val sender: MessageSender,
	@SerializedName("__v")
	val version: Int,
	//	@SerializedName("files")
//	val files: List<Any>,
	@SerializedName("_id")
	val id: String,
	@SerializedName("message")
	val message: String,
	@SerializedName("conversation")
	val conversation: MessageConversation,
	@SerializedName("updatedAt")
	val updatedAt: String,
) : Parcelable

//@Parcelize
//data class MessageSender(
//	@SerializedName("_id")
//	val id: String,
//	@SerializedName("name")
//	val name: String,
//	@SerializedName("email")
//	val email: String,
//	@SerializedName("picture")
//	val picture: String,
//	@SerializedName("status")
//	val status: String
//) : Parcelable

@Parcelize
data class MessageConversation(
	@SerializedName("_id")
	val id: String,
	@SerializedName("name")
	val name: String,
	@SerializedName("picture")
	val picture: String,
	@SerializedName("isGroup")
	val isGroup: Boolean,
	@SerializedName("users")
	val users: List<String>,
	@SerializedName("createdAt")
	val createdAt: String,
	@SerializedName("updatedAt")
	val updatedAt: String,
	@SerializedName("__v")
	val version: Int,
	@SerializedName("latestMessage")
	val latestMessage: String
) : Parcelable

@Parcelize
data class MessageUser(
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

data class SendMessageRequest(
	@SerializedName("conversationId")
	val conversationId: String,
	@SerializedName("message")
	val message: String
)

//data class SendMessageResponse(
//	@SerializedName("_id")
//	val id: String,
//	@SerializedName("sender")
//	val sender: MessageSender,
//	@SerializedName("message")
//	val message: String,
//	@SerializedName("conversation")
//	val conversation: MessageConversation,
////	@SerializedName("files") val files: List<Any>,
//	@SerializedName("createdAt")
//	val createdAt: String,
//	@SerializedName("updatedAt")
//	val updatedAt: String,
//	@SerializedName("__v")
//	val version: Int
//)

