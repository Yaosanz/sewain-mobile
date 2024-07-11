package com.sewain.mobileapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sewain.mobileapp.data.CatalogRepository
import com.sewain.mobileapp.data.UserRepository
import com.sewain.mobileapp.ui.screen.checkout.CheckoutViewModel
import com.sewain.mobileapp.ui.screen.create_catalog.CreateCatalogViewModel
import com.sewain.mobileapp.ui.screen.detail_catalog.DetailCatalogViewModel
import com.sewain.mobileapp.ui.screen.home.HomeScreenViewModel

class CatalogViewModelFactory(private val repository: CatalogRepository, private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(repository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(DetailCatalogViewModel::class.java)) {
            return DetailCatalogViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(repository, userRepository) as T
        }
        if (modelClass.isAssignableFrom(CreateCatalogViewModel::class.java)) {
            return CreateCatalogViewModel(repository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}