package com.combocart.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class NotificationDto(
    val id: String,
    val title: String,
    val message: String,
    val type: String,
    val created_at: String,
    val is_read: Boolean
)

interface NotificationApi {
    @GET("notifications")
    suspend fun getNotifications(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100
    ): List<NotificationDto>
}
