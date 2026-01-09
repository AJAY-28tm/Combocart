package com.combocart.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

data class UserDto(
    val id: String,
    val email: String?,
    val full_name: String?,
    val phone: String?,
    val avatar_url: String?,
    val is_active: Boolean = true
)

data class UserUpdateRequest(
    val full_name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val avatar_url: String? = null,
    val password: String? = null
)

interface UserApi {
    @GET("users/me")
    suspend fun getMe(): UserDto

    @PUT("users/me")
    suspend fun updateMe(@Body request: UserUpdateRequest): UserDto
}
