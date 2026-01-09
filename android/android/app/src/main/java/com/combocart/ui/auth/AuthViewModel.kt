package com.combocart.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    // For backward compatibility
    val authState get() = state

    // Step 1: Check if phone number is registered
    fun checkPhoneNumber(phone: String) {
        if (phone.length != 10) {
            _state.value = _state.value.copy(error = "Invalid Phone Number")
            return
        }
        
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = "")
            delay(1000) // Simulate network call
            
            val isRegistered = sessionManager.isUserRegistered(phone)
            _state.value = _state.value.copy(
                isLoading = false,
                phoneNumber = phone,
                isRegistered = isRegistered,
                navigateToNext = true
            )
        }
    }

    // Signup with password and confirm password
    fun signup(phone: String, password: String, confirmPassword: String) {
        val trimmedPhone = phone.trim()
        val trimmedPassword = password.trim()
        
        if (trimmedPassword != confirmPassword.trim()) {
            _state.value = _state.value.copy(error = "Passwords do not match")
            return
        }
        
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = "")
            delay(1000)
            
            // Generate a random OTP
            val otp = (1000..9999).random().toString()
            _state.value = _state.value.copy(
                isLoading = false,
                phoneNumber = trimmedPhone,
                generatedOtp = otp,
                showOtpDialog = true,
                tempPassword = trimmedPassword,
                isRegistered = false
            )
        }
    }

    // Step 2: Signup (legacy method)
    fun register(phone: String, pass: String) {
        val trimmedPhone = phone.trim()
        val trimmedPass = pass.trim()
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = "")
            delay(1000)
            
            // Generate a random OTP
            val otp = (1000..9999).random().toString()
            _state.value = _state.value.copy(
                isLoading = false,
                phoneNumber = trimmedPhone,
                generatedOtp = otp,
                showOtpDialog = true,
                tempPassword = trimmedPass
            )
        }
    }

    // Step 2: Login
    fun login(phone: String, pass: String) {
        val trimmedPhone = phone.trim()
        val trimmedPass = pass.trim()
        
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = "")
            delay(1000)
            
            if (sessionManager.verifyCredentials(trimmedPhone, trimmedPass)) {
                sessionManager.saveAuthToken("mock_token_$trimmedPhone")
                _state.value = _state.value.copy(
                    isLoading = false,
                    phoneNumber = trimmedPhone,
                    isRegistered = true,
                    isSuccess = true,
                    isOtpVerified = true
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Invalid phone number or password"
                )
            }
        }
    }

    // OTP Verification
    fun verifyOtp(otp: String) {
        if (otp == _state.value.generatedOtp) {
            // Correct OTP
            if (!_state.value.isRegistered) {
                // Save new user if signup
                sessionManager.saveUserCredentials(_state.value.phoneNumber, _state.value.tempPassword)
            }
            sessionManager.saveAuthToken("mock_token_${_state.value.phoneNumber}")
            _state.value = _state.value.copy(
                isSuccess = true, 
                isOtpVerified = true,
                showOtpDialog = false
            )
        } else {
            _state.value = _state.value.copy(error = "Invalid OTP. Demo OTP: ${_state.value.generatedOtp}")
        }
    }

    fun closeOtpDialog() {
        _state.value = _state.value.copy(showOtpDialog = false)
    }

    fun resetNavigation() {
        _state.value = _state.value.copy(navigateToNext = false, error = "")
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isOtpVerified: Boolean = false,
    val isRegistered: Boolean = false,
    val navigateToNext: Boolean = false,
    val phoneNumber: String = "",
    val error: String = "",
    val generatedOtp: String = "",
    val showOtpDialog: Boolean = false,
    val tempPassword: String = "" // For signup
)
