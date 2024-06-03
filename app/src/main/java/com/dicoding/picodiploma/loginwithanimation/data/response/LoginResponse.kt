package com.dicoding.picodiploma.loginwithanimation.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(
	@field:SerializedName("user")
	val user: User? = null,
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("error")
	val error: Error? = null
) : Parcelable

@Parcelize
data class User(
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("_id")
	val id: String? = null,
	@field:SerializedName("email")
	val email: String? = null,
	@field:SerializedName("picture")
	val picture: String? = null,
	@field:SerializedName("status")
	val status: String? = null,
	@field:SerializedName("token")
	val token: String? = null
) : Parcelable

