package com.dicoding.picodiploma.loginwithanimation.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class RefreshResponse(
	val message: String? = null,
	val user: User? = null
) : Parcelable

@Parcelize
data class RefreshUser(
	val name: String? = null,
	val id: String? = null,
	val email: String? = null,
	val picture: String? = null,
	val status: String? = null,
	val token: String? = null
) : Parcelable
