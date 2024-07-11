package com.sewain.mobileapp.ui.screen.detail_catalog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewain.mobileapp.data.CatalogRepository
import com.sewain.mobileapp.data.remote.response.CatalogItem
import kotlinx.coroutines.launch

class DetailCatalogViewModel(private val repository: CatalogRepository) : ViewModel() {

    var catalog = mutableStateOf<CatalogItem?>(null)
        private set

    fun fetchCatalog(catalogId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCatalogById(catalogId)
                if (response.isSuccessful) {
                    catalog.value = response.body()
                } else {
                    // Handle errors
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }
}