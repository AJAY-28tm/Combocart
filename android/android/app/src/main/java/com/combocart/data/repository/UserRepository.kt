package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.UserApi
import com.combocart.data.remote.UserDto
import com.combocart.data.remote.UserUpdateRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    fun getMe(): Flow<Resource<UserDto>> = flow {
        emit(Resource.Loading())
        try {
            val user = userApi.getMe()
            emit(Resource.Success(user))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    fun updateMe(request: UserUpdateRequest): Flow<Resource<UserDto>> = flow {
        emit(Resource.Loading())
        try {
            val user = userApi.updateMe(request)
            emit(Resource.Success(user))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
