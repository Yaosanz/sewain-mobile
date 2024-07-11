package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Catalog(
    val name: String,
    val description: String,
    val size: String,
    val price: Int,
    val status: String,
    @field:SerializedName("day_rent")
    val dayRent: Int,
    @field:SerializedName("day_maintenance")
    val dayMaintenance: Int,
    @field:SerializedName("photo_url")
    val photoUrl: String,
    @field:SerializedName("shop_id")
    val shopId: String,
)
