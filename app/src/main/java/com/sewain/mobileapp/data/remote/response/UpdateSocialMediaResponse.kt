package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateSocialMediaResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("results")
	val results: SocialMediaResults? = null
)

data class SocialMediaResults(
	@field:SerializedName("facebook_username")
	val facebookUsername: String? = null,

	@field:SerializedName("tiktok_username")
	val tiktokUsername: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("instagram_username")
	val instagramUsername: String? = null
)
