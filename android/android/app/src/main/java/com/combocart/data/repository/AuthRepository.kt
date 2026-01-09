package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.AuthApi
import com.combocart.data.remote.OtpRequest
import com.combocart.data.remote.VerifyOtpRequest
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepository @Inject constructor(
    private val api: AuthApi
) {
    fun generateOtp(phoneNumber: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.generateOtp(OtpRequest(phone_number = phoneNumber))
            emit(Resource.Success(response.msg))
        } catch(e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }

    fun verifyOtp(phoneNumber: String, otp: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.verifyOtp(VerifyOtpRequest(phone_number = phoneNumber, otp = otp))
            // Here we would typically save the token to SharedPreferences or DataStore
            emit(Resource.Success(response.access_token))
        } catch(e: HttpException) {
            emit(Resource.Error(e.message ?: "Invalid OTP or Server Error"))
        } catch(e: IOException) {
            emit(Resource.Error("Check your internet connection"))
        }
    }
}
