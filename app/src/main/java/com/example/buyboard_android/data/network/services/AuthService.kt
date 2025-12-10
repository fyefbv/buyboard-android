package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.dto.requests.LoginRequest
import com.example.buyboard_android.data.models.dto.requests.RefreshTokenRequest
import com.example.buyboard_android.data.models.dto.requests.RegisterRequest
import com.example.buyboard_android.data.models.dto.responses.TokenResponse
import com.example.buyboard_android.data.network.ApiException

class AuthService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

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

    suspend fun refreshToken(): TokenResponse {
        val refreshToken = apiClient.getAccessToken() ?: throw Exception("Нет токена")
        return try {
            val request = RefreshTokenRequest(refreshToken)
            apiClient.refreshToken(refreshToken)
        } catch (e: ApiException) {
            when (e.errorCode) {
                "invalid_token", "expired_token" -> {
                    logout()
                    throw Exception("Сессия истекла. Войдите снова")
                }
                else -> throw Exception("Ошибка обновления токена")
            }
        }
    }

    fun logout() {
        apiClient.clearTokens()
    }

    fun isLoggedIn(): Boolean = apiClient.isLoggedIn()

    fun getAccessToken(): String? = apiClient.getAccessToken()
}