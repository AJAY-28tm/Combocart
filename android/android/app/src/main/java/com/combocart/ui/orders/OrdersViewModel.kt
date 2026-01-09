package com.combocart.ui.orders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.OrderDto
import com.combocart.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OrdersState(
    val isLoading: Boolean = false,
    val orders: List<OrderDto> = emptyList(),
    val currentOrder: OrderDto? = null,
    val error: String? = null
)

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: OrderRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersState())
    val state: StateFlow<OrdersState> = _state.asStateFlow()

    init {
        getOrders()
        savedStateHandle.get<String>("orderId")?.let { orderId ->
            getOrder(orderId)
        }
    }

    fun getOrders() {
        viewModelScope.launch {
            repository.getOrders().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val orders = result.data ?: emptyList()
                        _state.value = _state.value.copy(orders = orders, isLoading = false)
                    }
                    is Resource.Error -> {
                        // Show error message when API fails
                        _state.value = _state.value.copy(
                            orders = emptyList(), 
                            isLoading = false, 
                            error = result.message ?: "Failed to load orders"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun getOrder(id: String) {
        viewModelScope.launch {
            repository.getOrderById(id).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(currentOrder = result.data, isLoading = false)
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            currentOrder = null, 
                            isLoading = false, 
                            error = result.message ?: "Failed to load order"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}
