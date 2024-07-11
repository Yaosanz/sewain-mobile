package com.sewain.mobileapp.data

import com.sewain.mobileapp.data.remote.retrofit.ApiService

class SewainRepository(
    private val apiService: ApiService
) {
    companion object {
        @Volatile
        private var instance: SewainRepository? = null

        fun getInstance(
            apiService: ApiService
        ): SewainRepository =
            instance ?: synchronized(this) {
                SewainRepository(apiService).apply {
                    instance = this
                }
            }
    }
}