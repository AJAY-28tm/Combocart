package com.combocart.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combocart.common.Resource
import com.combocart.data.remote.CartDto
import com.combocart.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartState(
    val isLoading: Boolean = false,
    val cart: CartDto? = null,
    val error: String? = null
)

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()
    
    // For one-time events like "Added to cart" toast
    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        // Observe cart changes from repository
        viewModelScope.launch {
            repository.cart.collect { cart ->
                _state.value = _state.value.copy(cart = cart, isLoading = false)
            }
        }
        getCart()
    }

    fun getCart() {
        // Disabled - using local cart only
        // viewModelScope.launch {
        //     repository.getCart().collect { result ->
        //         when (result) {
        //             is Resource.Success -> {
        //                 _state.value = CartState(cart = result.data)
        //             }
        //             is Resource.Error -> {
        //                 _state.value = CartState(error = result.message)
        //             }
        //             is Resource.Loading -> {
        //                 _state.value = CartState(isLoading = true)
        //             }
        //         }
        //     }
        // }
    }

    fun addToCart(recipeId: String, recipe: com.combocart.data.remote.RecipeDto? = null) {
        viewModelScope.launch {
            // Default qty 1 for now
            repository.addToCart(recipeId, recipe, 1).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CartState(cart = result.data)
                        _uiEvent.send("Added to cart!")
                    }
                    is Resource.Error -> {
                        _uiEvent.send("Error: ${result.message}")
                    }
                    is Resource.Loading -> {
                        // Optional: Show loading indicator on button
                    }
                }
            }
        }
    }
    
    fun removeFromCart(recipeId: String) {
        viewModelScope.launch {
            repository.removeFromCart(recipeId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CartState(cart = result.data)
                    }
                    is Resource.Error -> {
                         _uiEvent.send("Error removing item")
                    }
                    is Resource.Loading -> { }
                }
            }
        }
    }

    fun updateQuantity(recipeId: String, delta: Int) {
        viewModelScope.launch {
            // Use local cart manager directly for instant updates
            val localCartManager = repository.let { 
                (it as? com.combocart.data.repository.CartRepository)?.let { repo ->
                    // Access through reflection or add a public method
                    // For now, just use the repository method
                }
            }
            repository.addToCart(recipeId, null, delta).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = CartState(cart = result.data)
                    }
                    is Resource.Error -> {
                        _uiEvent.send("Error: ${result.message}")
                    }
                    is Resource.Loading -> { }
                }
            }
        }
    }
}
