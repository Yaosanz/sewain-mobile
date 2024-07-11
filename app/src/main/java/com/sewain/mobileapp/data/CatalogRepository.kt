package com.sewain.mobileapp.data

import androidx.lifecycle.MediatorLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sewain.mobileapp.data.local.entity.CatalogEntity
import com.sewain.mobileapp.data.local.room.CatalogDao
import com.sewain.mobileapp.data.local.room.SewainDatabase
import com.sewain.mobileapp.data.remote.model.AddTransactionResponse
import com.sewain.mobileapp.data.remote.model.Catalog
import com.sewain.mobileapp.data.remote.model.CreateTransaction
import com.sewain.mobileapp.data.remote.response.AddAttachmentsResponse
import com.sewain.mobileapp.data.remote.response.AddCatalogResponse
import com.sewain.mobileapp.data.remote.response.CatalogItem
import com.sewain.mobileapp.data.remote.response.CheckRatesResponse
import com.sewain.mobileapp.data.remote.response.PredictionResponse
import com.sewain.mobileapp.data.remote.retrofit.ApiService
import com.sewain.mobileapp.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class CatalogRepository private constructor(
    private val apiService: ApiService,
    private val catalogDao: CatalogDao,
    private val appExecutors: AppExecutors,
    private val sewainDatabase: SewainDatabase,
) {
    private val result = MediatorLiveData<Result<List<CatalogEntity>>>()

    fun getCatalogs(searchQuery : String, shopId: String? = null): Flow<PagingData<CatalogEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = CatalogsRemoteMediator(sewainDatabase, apiService, searchQuery, shopId),
            pagingSourceFactory = {
                sewainDatabase.catalogDao().getAllCatalogs()
            }
        ).flow
    }

    suspend fun getCatalogById(catalogId: String): Response<CatalogItem> {
        return apiService.getCatalogById(catalogId)
    }

    suspend fun predictionImage(imageFile: File): PredictionResponse {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val image = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        return apiService.predictionImage(image)
    }

    suspend fun addCatalog(catalog: Catalog): AddCatalogResponse {
        return apiService.addCatalog(catalog)
    }

    suspend fun checkRates(catalogId: String): Response<CheckRatesResponse> {
        return apiService.checkRates(catalogId)
    }

    suspend fun addTransaction(transaction: CreateTransaction): Response<AddTransactionResponse> {
        return apiService.addTransaction(transaction)
    }



    companion object {
        @Volatile
        private var instance: CatalogRepository? = null
        fun getInstance(
            apiService: ApiService,
            catalogDao: CatalogDao,
            appExecutors: AppExecutors,
            sewainDatabase: SewainDatabase,
        ): CatalogRepository =
            instance ?: synchronized(this) {
                instance ?: CatalogRepository(apiService, catalogDao, appExecutors, sewainDatabase )
            }.also { instance = it }
    }
}