package com.dicoding.picodiploma.loginwithanimation.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AccountResponse(
	val error: Boolean? = null,
	val message: String? = null
) : Parcelable
