package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.dto.requests.LoginRequest
import com.example.buyboard_android.data.models.dto.requests.RegisterRequest
import com.example.buyboard_android.data.models.dto.responses.TokenResponse
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthService(private val apiClient: ApiClient) {
    suspend fun register(login: String, email: String, password: String): TokenResponse {
        return try {
            val request = RegisterRequest(login, email, password)
            apiClient.register(request)
        } catch (e: ApiException) {
            when (e.errorCode) {
                "user_already_exists" -> throw Exception("Пользователь с таким логином или email уже существует")
                else -> throw Exception(e.message ?: "Ошибка регистрации")
            }
        }
    }

    suspend fun login(login: String, password: String): TokenResponse {
        return try {
            val request = LoginRequest(login, password)
            apiClient.login(request)
        } catch (e: ApiException) {
            when (e.errorCode) {
                "authentication_failed" -> throw Exception("Неверный логин или пароль")
                else -> throw Exception(e.message ?: "Ошибка входа")
            }
        }
    }

    fun logout() {
        apiClient.clearTokens()
    }

    fun isLoggedIn(): Boolean = apiClient.isLoggedIn()
}