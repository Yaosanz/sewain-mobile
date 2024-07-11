package com.sewain.mobileapp.data.remote.model

data class ChangePassword(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String,
)
