package com.combocart.data.repository

import com.combocart.common.Resource
import com.combocart.data.remote.NotificationApi
import com.combocart.data.remote.NotificationDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi
) {
    fun getNotifications(): Flow<Resource<List<NotificationDto>>> = flow {
        emit(Resource.Loading())
        try {
            val notifications = notificationApi.getNotifications()
            emit(Resource.Success(notifications))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Failed to fetch notifications"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
