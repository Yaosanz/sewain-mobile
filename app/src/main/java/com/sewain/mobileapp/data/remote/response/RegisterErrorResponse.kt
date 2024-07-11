package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterErrorResponse(

	@field:SerializedName("details")
	val details: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
