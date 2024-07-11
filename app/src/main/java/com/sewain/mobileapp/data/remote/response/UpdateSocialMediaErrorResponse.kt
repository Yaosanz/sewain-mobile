package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateSocialMediaErrorResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("results")
	val results: Any? = null
)
