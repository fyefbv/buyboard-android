package com.example.buyboard_android.data.network

import android.content.Context
import com.example.buyboard_android.data.models.dto.error.ApiErrorResponse
import com.example.buyboard_android.data.models.dto.requests.AdCreateRequest
import com.example.buyboard_android.data.models.dto.requests.AdUpdateRequest
import com.example.buyboard_android.data.models.dto.requests.LoginRequest
import com.example.buyboard_android.data.models.dto.requests.RefreshTokenRequest
import com.example.buyboard_android.data.models.dto.requests.RegisterRequest
import com.example.buyboard_android.data.models.dto.requests.UserUpdateRequest
import com.example.buyboard_android.data.models.dto.responses.AdResponse
import com.example.buyboard_android.data.models.dto.responses.CategoryResponse
import com.example.buyboard_android.data.models.dto.responses.FavoriteResponse
import com.example.buyboard_android.data.models.dto.responses.LocationResponse
import com.example.buyboard_android.data.models.dto.responses.TokenResponse
import com.example.buyboard_android.data.models.dto.responses.UserResponse
import com.example.buyboard_android.data.models.dto.responses.UserStatsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class ApiClient(context: Context) {

    private val baseUrl = "http://10.0.2.2:8000/api"

    private val tokenManager = TokenManager(context)

    private val client = HttpClient(OkHttp) {
        engine {
            config {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        defaultRequest {
            url("$baseUrl/")
            accept(ContentType.Application.Json)
        }
    }

    private fun getAuthHeader(): String? {
        return tokenManager.getAccessToken()?.let { "Bearer $it" }
    }

    suspend fun register(request: RegisterRequest): TokenResponse {
        return handleResponse {
            client.post("/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    suspend fun login(request: LoginRequest): TokenResponse {
        val response: TokenResponse = handleResponse {
            client.post("/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

        saveTokens(response.accessToken, response.refreshToken)

        return response
    }

    suspend fun refreshToken(refreshToken: String): TokenResponse {
        val request = RefreshTokenRequest(refreshToken)
        return handleResponse {
            client.post("/auth/refresh") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    suspend fun getCurrentUser(): UserResponse {
        return handleResponse {
            client.get("/users/me") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun getUserStats(): UserStatsResponse {
        return handleResponse {
            client.get("/users/me/stats") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun updateUser(request: UserUpdateRequest): UserResponse {
        return handleResponse {
            client.patch("/users/me") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
                setBody(request)
            }
        }
    }

    suspend fun getAds(params: Map<String, Any?> = emptyMap()): List<AdResponse> {
        return handleResponse {
            client.get("/ads/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }

                params.forEach { (key, value) ->
                    value?.let { parameter(key, it) }
                }
            }
        }
    }

    suspend fun getAd(adId: String): AdResponse {
        return handleResponse {
            client.get("/ads/$adId") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun getMyAds(): List<AdResponse> {
        return handleResponse {
            client.get("/ads/me/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun createAd(request: AdCreateRequest): AdResponse {
        return handleResponse {
            client.post("/ads/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
                setBody(request)
            }
        }
    }

    suspend fun updateAd(adId: String, request: AdUpdateRequest): AdResponse {
        return handleResponse {
            client.post("/ads/$adId") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
                header("X-HTTP-Method-Override", "PATCH")
                setBody(request)
            }
        }
    }

    suspend fun deleteAd(adId: String): Boolean {
        handleResponse<Unit> {
            client.delete("/ads/$adId") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
        return true
    }

    suspend fun getFavorites(): List<AdResponse> {
        return handleResponse {
            client.get("/favorites/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun addFavorite(adId: String): FavoriteResponse {
        return handleResponse {
            client.post("/favorites/$adId") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
                setBody("{}")
            }
        }
    }

    suspend fun removeFavorite(adId: String): Boolean {
        handleResponse<Unit> {
            client.delete("/favorites/$adId") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
        return true
    }

    suspend fun getCategories(): List<CategoryResponse> {
        return handleResponse {
            client.get("/categories/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    suspend fun getLocations(): List<LocationResponse> {
        return handleResponse {
            client.get("/locations/") {
                contentType(ContentType.Application.Json)
                getAuthHeader()?.let { bearerAuth(it) }
            }
        }
    }

    private suspend inline fun <reified T> handleResponse(
        request: () -> HttpResponse
    ): T {
        val response = request()
        val statusCode = response.status.value

        return if (statusCode in 200..299) {
            try {
                response.body<T>()
            } catch (e: Exception) {
                throw ApiException("Failed to parse response: ${e.message}", statusCode)
            }
        } else {
            val errorBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                "{}"
            }

            throw try {
                val errorResponse = Json.decodeFromString<ApiErrorResponse>(errorBody)
                ApiException(
                    errorResponse.error.message,
                    statusCode,
                    errorResponse.error.code
                )
            } catch (e: Exception) {
                ApiException("HTTP $statusCode: ${response.status.description}", statusCode)
            }
        }
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveTokens(accessToken, refreshToken)
    }

    fun clearTokens() {
        tokenManager.clearTokens()
    }

    fun isLoggedIn(): Boolean = tokenManager.isLoggedIn()

    fun getAccessToken(): String? = tokenManager.getAccessToken()
}