package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddCatalogResponse(

	@field:SerializedName("day_rent")
	val dayRent: Int? = null,

	@field:SerializedName("shop_id")
	val shopId: String? = null,

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("day_maintenance")
	val dayMaintenance: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
