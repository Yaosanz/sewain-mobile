package com.sewain.mobileapp.ui.screen.checkout

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewain.mobileapp.data.CatalogRepository
import com.sewain.mobileapp.data.UserRepository
import com.sewain.mobileapp.data.remote.model.AddTransactionResponse
import com.sewain.mobileapp.data.remote.model.CreateTransaction
import com.sewain.mobileapp.data.remote.response.CatalogItem
import com.sewain.mobileapp.data.remote.response.CheckRatesResponse
import kotlinx.coroutines.launch

class CheckoutViewModel(private val repository: CatalogRepository, private val userRepository: UserRepository) : ViewModel() {

    var catalog = mutableStateOf<CatalogItem?>(null)
        private set

    var checkRates = mutableStateOf<CheckRatesResponse?>(null)
        private set

    var addTransaction = mutableStateOf<AddTransactionResponse?>(null)
        private set

    fun fetchCatalog(catalogId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCatalogById(catalogId)
                if (response.isSuccessful) {
                    catalog.value = response.body()
                    checkRates(catalogId)
                } else {
                    // Handle errors
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    private fun checkRates(catalogId: String) {
        viewModelScope.launch {
            try {
                val response = repository.checkRates(catalogId)
                if (response.isSuccessful) {
                    checkRates.value = response.body()
                } else {
                    // Handle errors
                }
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    fun addTransaction(transaction: CreateTransaction, callback: (Result<AddTransactionResponse?>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.addTransaction(transaction)
                if (response.isSuccessful) {
                    addTransaction.value = response.body()
                    callback(Result.success(response.body()))
                } else {
                    // Handle errors
                }
            } catch (e: Exception) {
                callback(Result.failure(e))
                // Handle exceptions
            }
        }
    }
}