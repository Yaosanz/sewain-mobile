package com.sewain.mobileapp.data.remote.retrofit

import com.google.gson.annotations.SerializedName
import com.sewain.mobileapp.data.remote.model.AddTransactionResponse
import com.sewain.mobileapp.data.remote.model.Address
import com.sewain.mobileapp.data.remote.model.Catalog
import com.sewain.mobileapp.data.remote.model.ChangePassword
import com.sewain.mobileapp.data.remote.model.CreateTransaction
import com.sewain.mobileapp.data.remote.model.Login
import com.sewain.mobileapp.data.remote.model.Maps
import com.sewain.mobileapp.data.remote.model.Register
import com.sewain.mobileapp.data.remote.model.Shop
import com.sewain.mobileapp.data.remote.model.SocialMedia
import com.sewain.mobileapp.data.remote.model.User
import com.sewain.mobileapp.data.remote.response.AddAttachmentsResponse
import com.sewain.mobileapp.data.remote.response.AddCatalogResponse
import com.sewain.mobileapp.data.remote.response.CatalogItem
import com.sewain.mobileapp.data.remote.response.CatalogsResponse
import com.sewain.mobileapp.data.remote.response.ChangePasswordResponse
import com.sewain.mobileapp.data.remote.response.CheckRatesResponse
import com.sewain.mobileapp.data.remote.response.GetUserbyIDResponse
import com.sewain.mobileapp.data.remote.response.LoginResponse
import com.sewain.mobileapp.data.remote.response.PredictionResponse
import com.sewain.mobileapp.data.remote.response.RegisterResponse
import com.sewain.mobileapp.data.remote.response.UpdateDetailShopResponse
import com.sewain.mobileapp.data.remote.response.UpdateSocialMediaResponse
import com.sewain.mobileapp.data.remote.response.UpdateUserbyIDResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/v1/register")
    suspend fun register(@Body register: Register): RegisterResponse

    @POST("/api/v1/login")
    suspend fun login(@Body login: Login): LoginResponse

    @GET("/api/v1/catalogs")
    suspend fun getCatalogs(
        @Query("page") page: Int = 1,
        @Query("per_page") size: Int = 20,
        @Query("search") search: String? = null,
        @Query("shop_id") shop_id: String? = null
    ): CatalogsResponse

    @GET("/api/v1/users/{id}")
    suspend fun getUserById(@Path("id") id: String): GetUserbyIDResponse

    @PUT("/api/v1/users/{id}")
    suspend fun updateUserById(
        @Path("id") id: String,
        @Body user: User,
    ): UpdateUserbyIDResponse

    @Multipart
    @POST("/api/v1/attachments")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part
    ): AddAttachmentsResponse

    @GET("/api/v1/catalogs/{id}")
    suspend fun getCatalogById(@Path("id") catalogId: String): Response<CatalogItem>

    @PUT("/api/v1/users/{id}/update-password")
    suspend fun changePassword(
        @Path("id") id: String,
        @Body changePassword: ChangePassword,
    ): ChangePasswordResponse

    @PUT("/api/v1/social-media/{id}")
    suspend fun updateSocialMedia(
        @Path("id") id: String,
        @Body socialMedia: SocialMedia,
    ): UpdateSocialMediaResponse

    @PUT("/api/v1/detail-shops/{id}")
    suspend fun updateDetailShop(
        @Path("id") id: String,
        @Body shop: Shop,
    ): UpdateDetailShopResponse

    @Multipart
    @POST("/api/v1/ml/prediction")
    suspend fun predictionImage(
        @Part image: MultipartBody.Part
    ): PredictionResponse

    @POST("/api/v1/catalogs")
    suspend fun addCatalog(
        @Body catalog: Catalog
    ): AddCatalogResponse

    @PUT("/api/v1/users/{id}")
    suspend fun updateAddress(
        @Path("id") id: String,
        @Body address: Address
    ): UpdateUserbyIDResponse

    @PUT("/api/v1/users/{id}")
    suspend fun updateMap(
        @Path("id") id: String,
        @Body maps: Maps
    ): UpdateUserbyIDResponse

    @GET("/api/v1/check_rates/{id}")
    suspend fun checkRates(@Path("id") catalogId: String): Response<CheckRatesResponse>

    @POST("/api/v1/transactions")
    suspend fun addTransaction(
        @Body transaction: CreateTransaction
    ): Response<AddTransactionResponse>
}