package com.combocart.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class OrderRequest(
    val address_line: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

// Order item with recipe details
data class OrderItemDto(
    val id: String,
    val recipe_id: String,
    val recipe_title: String,
    val price_per_unit: Double,
    val quantity: Int,
    val recipe: RecipeDto? = null
)

data class OrderDto(
    val id: String,
    val user_id: String,
    val total_amount: Double,
    val status: String,
    val address_line: String,
    val created_at: String,
    val items: List<OrderItemDto> = emptyList()
)

interface OrderApi {
    @POST("orders/")
    suspend fun placeOrder(@Body request: OrderRequest): OrderDto

    @GET("orders/")
    suspend fun getOrders(): List<OrderDto>

    @GET("orders/{id}")
    suspend fun getOrderById(@retrofit2.http.Path("id") id: String): OrderDto

    @POST("payments/process")
    suspend fun processPayment(@Body request: com.combocart.data.remote.PaymentRequest): com.combocart.data.remote.PaymentResponse
}

data class PaymentRequest(
    val order_id: String,
    val amount: Double,
    val provider: String = "MockPay"
)

data class PaymentResponse(
    val id: String,
    val transaction_id: String,
    val status: String,
    val message: String
)
