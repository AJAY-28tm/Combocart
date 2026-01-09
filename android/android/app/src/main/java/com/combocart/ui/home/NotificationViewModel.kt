package com.combocart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.NotificationDto
import com.combocart.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationDto> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    init {
        getNotifications()
    }

    fun getNotifications() {
        viewModelScope.launch {
            repository.getNotifications().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = NotificationState(notifications = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        // Fallback to mock data if API fails (e.g., not logged in)
                        val mockNotifications = listOf(
                            NotificationDto(
                                id = "notif_1",
                                title = "Welcome to ComboCart!",
                                message = "Start exploring delicious meal kits and combos.",
                                type = "promo",
                                created_at = "2026-01-02T10:00:00",
                                is_read = false
                            ),
                            NotificationDto(
                                id = "notif_2",
                                title = "New Recipe Added",
                                message = "Check out our new Butter Chicken Combo!",
                                type = "update",
                                created_at = "2026-01-01T15:30:00",
                                is_read = true
                            ),
                            NotificationDto(
                                id = "notif_3",
                                title = "Special Offer",
                                message = "Get 20% off on your first order. Use code: COMBO20",
                                type = "promo",
                                created_at = "2025-12-31T09:00:00",
                                is_read = true
                            )
                        )
                        _state.value = NotificationState(notifications = mockNotifications)
                    }
                    is Resource.Loading -> {
                        _state.value = NotificationState(isLoading = true)
                    }
                }
            }
        }
    }
}
