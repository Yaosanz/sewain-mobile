package com.sewain.mobileapp.ui.screen.home

import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sewain.mobileapp.data.CatalogRepository
import com.sewain.mobileapp.data.UserRepository
import com.sewain.mobileapp.data.local.entity.CatalogEntity
import com.sewain.mobileapp.utils.uriToFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class HomeScreenViewModel(
    private val catalogRepository: CatalogRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private lateinit var imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    private val _message = MutableStateFlow("")
    private val _success = MutableStateFlow(false)
    private val _shopId = MutableStateFlow<String?>(null)
    val shopId: StateFlow<String?>
        get() = _shopId

    private val _data = MutableStateFlow("")
    val data: StateFlow<String>
        get() = _data

    val success: StateFlow<Boolean>
        get() = _success

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean>
        get() = _loading
    val message: StateFlow<String>
        get() = _message

    @OptIn(ExperimentalCoroutinesApi::class)
    val catalogs: Flow<PagingData<CatalogEntity>> = searchQuery.flatMapLatest { query ->
        catalogRepository.getCatalogs(query, shopId.value).cachedIn(viewModelScope)
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setShopId(shopId: String?) {
        _shopId.value = shopId
    }

    private suspend fun predictImage(imageFile: File) {
        _loading.value = true
        try {
            val data = catalogRepository.predictionImage(imageFile)
            setSearchQuery(data.data?.animeClassification?.replace("_", " ") ?: "")
            _message.value = "Success: predict image successful."
        } catch (e: HttpException) {
            // get error message
            _message.value = "Error: upload image failure."
        } catch (e: Exception) {
            _message.value = "Error: upload image failure."
        } finally {
            _loading.value = false
        }
    }

    fun processImagePrediction(imageFile: File) {
        viewModelScope.launch {
            predictImage(imageFile)
        }
    }

    fun initImagePickerLauncher(imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>) {
        this.imagePickerLauncher = imagePickerLauncher
    }

    fun pickImageFromGallery() {
        imagePickerLauncher.launch("image/*")
    }

    fun getUserById(id: String) {
        viewModelScope.launch {
            val data = userRepository.getUserById(id).userResults?.detailsUser?.detailShopId
            _data.value = data!!
        }
    }

}