package com.dicoding.picodiploma.loginwithanimation.data.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val email: String,
    val name: String,
    val accessToken: String,
    val picture: String,
    val status: String,
    val refreshToken: String,
    val isLogin: Boolean = false
) : Parcelable