package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class DetailsUser(
    @field:SerializedName("full_name")
    val fullName: String? = null,

    @field:SerializedName("photo_url")
    val photoUrl: String? = null,
)
