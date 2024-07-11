package com.sewain.mobileapp.data

import com.sewain.mobileapp.data.local.model.SessionModel
import com.sewain.mobileapp.data.local.preferences.SessionPreferences
import com.sewain.mobileapp.data.remote.model.Address
import com.sewain.mobileapp.data.remote.model.ChangePassword
import com.sewain.mobileapp.data.remote.model.DetailsUser
import com.sewain.mobileapp.data.remote.model.Login
import com.sewain.mobileapp.data.remote.model.Maps
import com.sewain.mobileapp.data.remote.model.Register
import com.sewain.mobileapp.data.remote.model.Shop
import com.sewain.mobileapp.data.remote.model.SocialMedia
import com.sewain.mobileapp.data.remote.model.User
import com.sewain.mobileapp.data.remote.response.AddAttachmentsResponse
import com.sewain.mobileapp.data.remote.response.ChangePasswordResponse
import com.sewain.mobileapp.data.remote.response.GetUserbyIDResponse
import com.sewain.mobileapp.data.remote.response.LoginResponse
import com.sewain.mobileapp.data.remote.response.RegisterResponse
import com.sewain.mobileapp.data.remote.response.UpdateDetailShopResponse
import com.sewain.mobileapp.data.remote.response.UpdateSocialMediaResponse
import com.sewain.mobileapp.data.remote.response.UpdateUserbyIDResponse
import com.sewain.mobileapp.data.remote.retrofit.ApiService
import com.sewain.mobileapp.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepository private constructor(
    private val userPreference: SessionPreferences,
    private val appExecutors: AppExecutors,
    private val apiService: ApiService
) {

    suspend fun saveSession(id: String, token: String, isShop: Boolean) {
        userPreference.saveSession(SessionModel(id, token, isShop))
    }

    fun getSession(): Flow<SessionModel> {
        return userPreference.getSession()
    }

    suspend fun registerUser(username: String, email: String, password: String): RegisterResponse {
        return apiService.register(Register(username, email, password))
    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        return apiService.login(Login(email, password))
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun uploadImage(imageFile: File): AddAttachmentsResponse {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val image = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )

        return apiService.uploadImage(image)
    }

    suspend fun getUserById(id: String): GetUserbyIDResponse {
        return apiService.getUserById(id)
    }

    suspend fun updateUserById(
        id: String,
        fullName: String,
        username: String,
        email: String,
        photoUrl: String,
    ): UpdateUserbyIDResponse {
        return apiService.updateUserById(id, User(username, email, DetailsUser(fullName = fullName, photoUrl = photoUrl)))
    }

    suspend fun changePassword(
        id: String,
        currentPassword: String,
        newPassword: String,
        confirmPassword: String,
    ): ChangePasswordResponse {
        return apiService.changePassword(id, ChangePassword(currentPassword, newPassword, confirmPassword))
    }

    suspend fun updateSocialMedia(
        id: String,
        facebook: String,
        instagram: String,
        tiktok: String,
    ): UpdateSocialMediaResponse {
        return apiService.updateSocialMedia(id, SocialMedia(facebook, instagram, tiktok))
    }

    suspend fun updateDetailShop(
        id: String,
        name: String,
        username: String,
    ): UpdateDetailShopResponse {
        return apiService.updateDetailShop(id, Shop(name, username))
    }

    suspend fun updateAddress(
        id: String,
        address: Address
    ): UpdateUserbyIDResponse {
        return apiService.updateAddress(id, address)
    }

    suspend fun updateMap(
        id: String,
        maps: Maps
    ): UpdateUserbyIDResponse {
        return apiService.updateMap(id, maps)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: SessionPreferences,
            appExecutors: AppExecutors,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, appExecutors, apiService)
            }.also { instance = it }
    }
}