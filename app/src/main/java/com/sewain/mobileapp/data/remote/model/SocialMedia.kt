package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class SocialMedia(
    @field:SerializedName("facebook_username")
    val facebook: String,
    @field:SerializedName("instagram_username")
    val instagram: String,
    @field:SerializedName("tiktok_username")
    val tiktok: String,
)
