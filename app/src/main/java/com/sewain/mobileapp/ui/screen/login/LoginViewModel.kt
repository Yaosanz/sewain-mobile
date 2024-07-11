package com.sewain.mobileapp.ui.screen.login

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sewain.mobileapp.data.UserRepository
import com.sewain.mobileapp.data.remote.response.RegisterErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _signInMessage = MutableStateFlow("")
    val signInMessage: StateFlow<String>
        get() = _signInMessage

    private val _signInSuccess = MutableStateFlow(false)
    val signInSuccess: StateFlow<Boolean>
        get() = _signInSuccess

    private val _isSignInLoading = MutableStateFlow(false)
    val isSignInLoading: StateFlow<Boolean>
        get() = _isSignInLoading

    suspend fun login(email: String, password: String) {
        _isSignInLoading.value = true
        try {
            //get success message
            val data = repository.loginUser(email, password)
            _signInMessage.value = "Success: ${data.message}"
            _signInSuccess.value = true
            repository.saveSession(data.results?.id!!, data.results.token!!, false)
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterErrorResponse::class.java)
            val errorMessage = errorBody.message
            _signInMessage.value = "Error: $errorMessage"
            _signInSuccess.value = false
        } catch (e: SocketTimeoutException) {
            _signInMessage.value = "Error: Timeout! ${e.message}"
            _signInSuccess.value = false
        }
    }
}