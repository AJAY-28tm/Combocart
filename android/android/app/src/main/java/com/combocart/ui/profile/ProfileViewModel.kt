package com.combocart.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.UserDto
import com.combocart.data.remote.UserUpdateRequest
import com.combocart.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val isLoading: Boolean = false,
    val user: UserDto? = null,
    val error: String? = null,
    val isUpdateSuccess: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            repository.getMe().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(user = result.data, isLoading = false)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun updateProfile(full_name: String?, email: String?, phone: String?) {
        viewModelScope.launch {
            val request = UserUpdateRequest(full_name = full_name, email = email, phone = phone)
            repository.updateMe(request).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(user = result.data, isLoading = false, isUpdateSuccess = true)
                        _uiEvent.send("Profile updated successfully!")
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.message, isLoading = false)
                        _uiEvent.send(result.message ?: "Failed to update profile")
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
    
    fun resetUpdateStatus() {
        _state.value = _state.value.copy(isUpdateSuccess = false)
    }
}
