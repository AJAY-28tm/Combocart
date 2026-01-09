package com.combocart.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.OrderDto
import com.combocart.data.remote.PaymentResponse
import com.combocart.data.repository.OrderRepository
import com.combocart.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CheckoutState(
    val isLoading: Boolean = false,
    val order: OrderDto? = null,
    val paymentResponse: PaymentResponse? = null,
    val error: String? = null,
    val selectedAddress: String = "123, Street Name, City, PIN 123456",
    val selectedPaymentMethod: String = "Credit Card"
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: OrderRepository,
    private val cartRepository: CartRepository,
    private val localCartManager: com.combocart.data.local.LocalCartManager
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutState())
    val state: StateFlow<CheckoutState> = _state.asStateFlow()

    fun placeOrder() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            // 1. Sync local cart items to backend cart first
            cartRepository.syncLocalCart()
            
            // 2. Place order on backend
            repository.placeOrder(_state.value.selectedAddress).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val order = result.data
                        if (order != null) {
                            _state.value = _state.value.copy(order = order)
                            // Automatically start payment process
                            startRazorpayPayment(order.id, order.total_amount)
                        } else {
                            _state.value = _state.value.copy(isLoading = false, error = "Order creation failed")
                        }
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun startRazorpayPayment(orderId: String, amount: Double) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            // Simulate Razorpay SDK UI interaction delay
            kotlinx.coroutines.delay(2000)
            
            // 3. Process payment on backend
            repository.processPayment(orderId, amount).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        localCartManager.clearCart()
                        _state.value = _state.value.copy(
                            paymentResponse = result.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}
