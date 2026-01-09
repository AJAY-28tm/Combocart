package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.AddToCartRequest
import com.combocart.data.remote.CartApi
import com.combocart.data.remote.CartDto
import com.combocart.data.remote.RecipeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Singleton
class CartRepository @Inject constructor(
    private val cartApi: CartApi,
    private val localCartManager: com.combocart.data.local.LocalCartManager
) {
    private val _cart = MutableStateFlow<CartDto?>(null)
    val cart: StateFlow<CartDto?> = _cart.asStateFlow()

    init {
        // Start with local cart
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            localCartManager.cart.collect { localCart ->
                _cart.emit(localCart)
            }
        }
    }

    private suspend fun updateCartState(newCart: CartDto) {
        _cart.emit(newCart)
    }
    fun getCart(): Flow<Resource<CartDto>> = flow {
        emit(Resource.Loading())
        try {
            val cart = cartApi.getCart()
            updateCartState(cart)
            emit(Resource.Success(cart))
        } catch (e: HttpException) {
             if (e.code() == 404) {
                 val emptyCart = CartDto("", emptyList(), 0.0)
                 updateCartState(emptyCart)
                 emit(Resource.Success(emptyCart))
             } else {
                 emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
             }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    fun addToCart(recipeId: String, recipe: RecipeDto?, quantity: Int): Flow<Resource<CartDto>> = flow {
        emit(Resource.Loading())
        try {
            // Update local cart immediately
            localCartManager.addToCart(recipeId, recipe, quantity)
            
            // Try to sync with backend in background
            try {
                val cart = cartApi.addToCart(AddToCartRequest(recipeId, quantity))
                updateCartState(cart)
                emit(Resource.Success(cart))
            } catch (e: Exception) {
                // If API fails, still return success with local cart
                localCartManager.cart.value?.let { emit(Resource.Success(it)) }
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to add to cart"))
        }
    }
    
    fun removeFromCart(recipeId: String): Flow<Resource<CartDto>> = flow {
        emit(Resource.Loading())
        try {
            // Update local cart immediately
            localCartManager.removeFromCart(recipeId)
            
            // Try to sync with backend
            try {
                val cart = cartApi.removeFromCart(recipeId)
                updateCartState(cart)
                emit(Resource.Success(cart))
            } catch (e: Exception) {
                // If API fails, still return success with local cart
                localCartManager.cart.value?.let { emit(Resource.Success(it)) }
            }
        } catch (e: Exception) {
             emit(Resource.Error(e.localizedMessage ?: "Failed to remove item"))
        }
    }

    suspend fun syncLocalCart() {
        val items = localCartManager.cart.value?.items ?: return
        for (item in items) {
            try {
                cartApi.addToCart(AddToCartRequest(item.recipe_id, item.quantity))
            } catch (e: Exception) {
                // Log or ignore individual sync failures
            }
        }
    }
}
