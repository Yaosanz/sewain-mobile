package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Maps(
    @field:SerializedName("address_users")
    val mapsBody: MapsBody,
)

data class MapsBody(
    val latitude: Double,
    val longitude: Double,
)
