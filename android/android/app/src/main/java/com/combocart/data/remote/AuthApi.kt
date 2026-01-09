package com.combocart.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login/otp/generate")
    suspend fun generateOtp(@Body request: OtpRequest): OtpResponse

    @POST("login/otp/verify")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): AuthResponse
}

data class OtpRequest(
    val phone_number: String
)

data class OtpResponse(
    val msg: String
)

data class VerifyOtpRequest(
    val phone_number: String,
    val otp: String
)

data class AuthResponse(
    val access_token: String,
    val token_type: String
)
