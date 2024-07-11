package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Address(
    @field:SerializedName("address_users")
    val addressBody: AddressBody,
)

data class AddressBody(
    @field:SerializedName("recipient_name")
    val recipientName: String,

    @field:SerializedName("full_address")
    val fullAddress: String,

    @field:SerializedName("number_phone")
    val numberPhone: String,
)
