package com.sewain.mobileapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CheckRatesResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("origin")
	val origin: Origin? = null,

	@field:SerializedName("destination")
	val destination: Destination? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("pricing")
	val pricing: List<PricingItem> = emptyList(),

)

data class Destination(

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("administrative_division_level_3_name")
	val administrativeDivisionLevel3Name: String? = null,

	@field:SerializedName("administrative_division_level_2_type")
	val administrativeDivisionLevel2Type: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("location_id")
	val locationId: Any? = null,

	@field:SerializedName("administrative_division_level_4_type")
	val administrativeDivisionLevel4Type: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("administrative_division_level_1_type")
	val administrativeDivisionLevel1Type: String? = null,

	@field:SerializedName("administrative_division_level_2_name")
	val administrativeDivisionLevel2Name: String? = null,

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("administrative_division_level_1_name")
	val administrativeDivisionLevel1Name: String? = null,

	@field:SerializedName("administrative_division_level_3_type")
	val administrativeDivisionLevel3Type: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: Int? = null,

	@field:SerializedName("administrative_division_level_4_name")
	val administrativeDivisionLevel4Name: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class PricingItem(

	@field:SerializedName("available_for_insurance")
	val availableForInsurance: Boolean? = null,

	@field:SerializedName("shipment_duration_unit")
	val shipmentDurationUnit: String? = null,

	@field:SerializedName("courier_service_name")
	val courierServiceName: String? = null,

	@field:SerializedName("shipping_type")
	val shippingType: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("available_for_cash_on_delivery")
	val availableForCashOnDelivery: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("service_type")
	val serviceType: String? = null,

	@field:SerializedName("courier_name")
	val courierName: String? = null,

	@field:SerializedName("shipment_duration_range")
	val shipmentDurationRange: String? = null,

	@field:SerializedName("available_for_instant_waybill_id")
	val availableForInstantWaybillId: Boolean? = null,

	@field:SerializedName("courier_code")
	val courierCode: String? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("courier_service_code")
	val courierServiceCode: String? = null,

	@field:SerializedName("available_collection_method")
	val availableCollectionMethod: List<String?>? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("available_for_proof_of_delivery")
	val availableForProofOfDelivery: Boolean? = null
)

data class Origin(

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("administrative_division_level_3_name")
	val administrativeDivisionLevel3Name: String? = null,

	@field:SerializedName("administrative_division_level_2_type")
	val administrativeDivisionLevel2Type: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("location_id")
	val locationId: Any? = null,

	@field:SerializedName("administrative_division_level_4_type")
	val administrativeDivisionLevel4Type: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("administrative_division_level_1_type")
	val administrativeDivisionLevel1Type: String? = null,

	@field:SerializedName("administrative_division_level_2_name")
	val administrativeDivisionLevel2Name: String? = null,

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("administrative_division_level_1_name")
	val administrativeDivisionLevel1Name: String? = null,

	@field:SerializedName("administrative_division_level_3_type")
	val administrativeDivisionLevel3Type: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: Int? = null,

	@field:SerializedName("administrative_division_level_4_name")
	val administrativeDivisionLevel4Name: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)
