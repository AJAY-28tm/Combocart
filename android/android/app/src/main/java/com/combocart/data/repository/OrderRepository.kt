package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.OrderApi
import com.combocart.data.remote.OrderDto
import com.combocart.data.remote.OrderRequest
import com.combocart.data.remote.PaymentRequest
import com.combocart.data.remote.PaymentResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderApi: OrderApi
) {
    fun placeOrder(addressLine: String): Flow<Resource<OrderDto>> = flow {
        emit(Resource.Loading())
        try {
            val order = orderApi.placeOrder(OrderRequest(address_line = addressLine))
            emit(Resource.Success(order))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to place order"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server."))
        }
    }

    fun processPayment(orderId: String, amount: Double): Flow<Resource<PaymentResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = orderApi.processPayment(PaymentRequest(order_id = orderId, amount = amount))
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Payment failed"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server."))
        }
    }

    fun getOrders(): Flow<Resource<List<OrderDto>>> = flow {
        emit(Resource.Loading())
        try {
            val orders = orderApi.getOrders()
            emit(Resource.Success(orders))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to fetch orders"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server."))
        }
    }

    fun getOrderById(id: String): Flow<Resource<OrderDto>> = flow {
        emit(Resource.Loading())
        try {
            val order = orderApi.getOrderById(id)
            emit(Resource.Success(order))
        } catch (e: HttpException) {
             emit(Resource.Error(e.localizedMessage ?: "Failed to fetch order details"))
        } catch (e: IOException) {
             emit(Resource.Error("Couldn't reach server."))
        }
    }
}
