package com.combocart.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class CartDto(
    val user_id: String,
    val items: List<CartItemDto>,
    val total_price: Double
)

data class CartItemDto(
    val id: String?,
    val recipe_id: String,
    val quantity: Int,
    val recipe: RecipeDto? // Nested recipe object from backend
)

data class AddToCartRequest(
    val recipe_id: String,
    val quantity: Int
)

interface CartApi {
    @GET("orders/cart")
    suspend fun getCart(): CartDto

    @POST("orders/cart")
    suspend fun addToCart(@Body request: AddToCartRequest): CartDto

    @DELETE("orders/cart/{recipeId}")
    suspend fun removeFromCart(@Path("recipeId") recipeId: String): CartDto
}
