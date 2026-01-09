package com.combocart.data.local

import com.combocart.data.remote.CartDto
import com.combocart.data.remote.CartItemDto
import com.combocart.data.remote.RecipeDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalCartManager @Inject constructor() {
    
    private val _cartItems = MutableStateFlow<MutableMap<String, CartItemDto>>(mutableMapOf())
    private val _cart = MutableStateFlow<CartDto?>(null)
    val cart: StateFlow<CartDto?> = _cart.asStateFlow()
    
    init {
        updateCart()
    }
    
    fun addToCart(recipeId: String, recipe: RecipeDto?, quantity: Int = 1) {
        val currentItems = _cartItems.value.toMutableMap()
        val existingItem = currentItems[recipeId]
        
        if (existingItem != null) {
            currentItems[recipeId] = existingItem.copy(
                quantity = existingItem.quantity + quantity
            )
        } else {
            currentItems[recipeId] = CartItemDto(
                id = recipeId,
                recipe_id = recipeId,
                quantity = quantity,
                recipe = recipe
            )
        }
        
        _cartItems.value = currentItems
        updateCart()
    }
    
    fun removeFromCart(recipeId: String) {
        val currentItems = _cartItems.value.toMutableMap()
        currentItems.remove(recipeId)
        _cartItems.value = currentItems
        updateCart()
    }
    
    fun updateQuantity(recipeId: String, delta: Int) {
        val currentItems = _cartItems.value.toMutableMap()
        val existingItem = currentItems[recipeId] ?: return
        
        val newQuantity = existingItem.quantity + delta
        if (newQuantity <= 0) {
            currentItems.remove(recipeId)
        } else {
            currentItems[recipeId] = existingItem.copy(quantity = newQuantity)
        }
        
        _cartItems.value = currentItems
        updateCart()
    }
    
    fun clearCart() {
        _cartItems.value = mutableMapOf()
        updateCart()
    }
    
    private fun updateCart() {
        val items = _cartItems.value.values.toList()
        val total = items.sumOf { (it.recipe?.price ?: 0.0) * it.quantity }
        
        _cart.value = CartDto(
            user_id = "local_user",
            items = items,
            total_price = total
        )
    }
}
