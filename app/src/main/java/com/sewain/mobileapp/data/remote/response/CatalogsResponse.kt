package com.sewain.mobileapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogsResponse(
	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("page_context")
	val pageContext: PageContext? = null,

	@field:SerializedName("results")
	val results: List<CatalogItem> = emptyList()
) : Parcelable

@Parcelize
data class CatalogItem(

	@field:SerializedName("day_rent")
	val dayRent: Int? = null,

	@field:SerializedName("shop_id")
	val shopId: String? = null,

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("price")
	val price: Double = 0.0,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("day_maintenance")
	val dayMaintenance: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("photo_url")
	val photoUrl: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("shop")
	val shop: ShopCatalog? = null
) : Parcelable

@Parcelize
data class ShopCatalog(
	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable

@Parcelize
data class PageContext(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null
) : Parcelable

