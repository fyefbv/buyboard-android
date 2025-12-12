package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.domain.User
import com.example.buyboard_android.data.models.dto.requests.UserUpdateRequest
import com.example.buyboard_android.data.models.extensions.toDomain
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService(private val apiClient: ApiClient) {
    suspend fun getCurrentUser(): User {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getCurrentUser()
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка загрузки профиля")
            }
        }
    }

    suspend fun getUserById(userId: String): User {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getUser(userId)
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка загрузки профиля")
            }
        }
    }

    suspend fun getUserStats(): Int {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getUserStats()
                response.activeAdsCount
            } catch (e: ApiException) {
                0
            }
        }
    }

    suspend fun updateUser(login: String? = null, email: String? = null, password: String? = null): User {
        return withContext(Dispatchers.IO) {
            try {
                val request = UserUpdateRequest(login, email, password)
                val response = apiClient.updateUser(request)
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка обновления профиля")
            }
        }
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