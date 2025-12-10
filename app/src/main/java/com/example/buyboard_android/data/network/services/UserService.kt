package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.domain.User
import com.example.buyboard_android.data.models.dto.requests.UserUpdateRequest
import com.example.buyboard_android.data.models.extensions.toDomain

class UserService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

    suspend fun getCurrentUser(): User {
        val response = apiClient.getCurrentUser()
        return response.toDomain()
    }

    suspend fun getUserStats(): Int {
        val response = apiClient.getUserStats()
        return response.activeAdsCount
    }

    suspend fun getUserById(userId: String): User {
        // TODO: Реализовать, когда появится соответствующий endpoint
        throw NotImplementedError("getUserById not implemented yet")
    }

    suspend fun updateUser(
        login: String? = null,
        email: String? = null,
        password: String? = null
    ): User {
        val request = UserUpdateRequest(login, email, password)
        val response = apiClient.updateUser(request)
        return response.toDomain()
    }

    suspend fun updateAvatar(imageBytes: ByteArray): String {
        // TODO: Реализовать загрузку аватара
        throw NotImplementedError("updateAvatar not implemented yet")
    }

    suspend fun deleteUser(): Boolean {
        // TODO: Реализовать удаление пользователя
        throw NotImplementedError("deleteUser not implemented yet")
    }
}