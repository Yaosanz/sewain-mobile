package com.sewain.mobileapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class AddTransactionResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Invoice(

	@field:SerializedName("invoice_id")
	val invoiceId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("detail")
	val detail: Detail? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Detail(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("invoice_url")
	val invoiceUrl: String? = null
)

data class Data(

	@field:SerializedName("invoice")
	val invoice: Invoice? = null
)
