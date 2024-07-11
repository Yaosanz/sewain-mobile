package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateTransaction(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("courier")
	val courier: Courier? = null,

	@field:SerializedName("catalog")
	val catalog: CatalogTransaction? = null,

	@field:SerializedName("end_rent_date")
	val endRentDate: String? = null,

	@field:SerializedName("start_rent_date")
	val startRentDate: String? = null
)

data class Courier(

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class CatalogTransaction(

	@field:SerializedName("id")
	val id: String? = null
)
