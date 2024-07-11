package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateUserbyIDResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("results")
	val results: Results? = null
)

data class SocialMediaUser(

	@field:SerializedName("facebook_username")
	val facebookUsername: String? = null,

	@field:SerializedName("tiktok_username")
	val tiktokUsername: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("instagram_username")
	val instagramUsername: String? = null
)

data class Results(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("DetailsUser")
	val detailsUser: DetailsUser? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class DetailsUser(

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("social_media_id")
	val socialMediaId: String? = null,

	@field:SerializedName("address_user_id")
	val addressUserId: String? = null,

	@field:SerializedName("address_user")
	val addressUser: AddressUser? = null,

	@field:SerializedName("detail_shop_id")
	val detailShopId: String? = null,

	@field:SerializedName("users_id")
	val usersId: String? = null,

	@field:SerializedName("detail_shop")
	val detailShop: DetailShop? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("social_media_user")
	val socialMediaUser: SocialMediaUser? = null,

	@field:SerializedName("number_phone")
	val numberPhone: String? = null
)

data class AddressUser(

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("full_address")
	val fullAddress: String? = null,

	@field:SerializedName("recipient_name")
	val recipientName: String? = null,

	@field:SerializedName("number_phone")
	val numberPhone: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class DetailShop(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
