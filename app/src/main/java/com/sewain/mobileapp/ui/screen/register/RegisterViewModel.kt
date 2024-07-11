package com.sewain.mobileapp.ui.screen.register

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.sewain.mobileapp.data.UserRepository
import com.sewain.mobileapp.data.remote.response.RegisterErrorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _signupMessage = MutableStateFlow("")
    val signupMessage: StateFlow<String>
        get() = _signupMessage

    private val _signupSuccess = MutableStateFlow(false)
    val signupSuccess: StateFlow<Boolean>
        get() = _signupSuccess

    private val _isSignupLoading = MutableStateFlow(false)
    val isSignupLoading: StateFlow<Boolean>
        get() = _isSignupLoading

    suspend fun register(name: String, email: String, password: String) {
        _isSignupLoading.value = true
        try {
            //get success message
            val message = repository.registerUser(name, email, password)
            _signupMessage.value = "Success: ${message.message}"
            _signupSuccess.value = true
        } catch (e: HttpException) {
            //get error message
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, RegisterErrorResponse::class.java)
            val errorMessage = errorBody.message
            _signupMessage.value = "Error: $errorMessage"
            _signupSuccess.value = false
        } catch (e: SocketTimeoutException) {
            _signupMessage.value = "Error: Timeout! ${e.message}"
            _signupSuccess.value = false
        }
    }
}