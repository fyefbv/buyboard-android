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
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
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

class ApiClient(context: Context, private val tokenManager: TokenManager) {

    private val baseUrl = "http://10.0.2.2:8000"

    private var currentLanguage = "en"

    fun setLanguage(language: String) {
        currentLanguage = language
    }

    private val client = HttpClient(OkHttp) {
        engine {
            config {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
            }
            addInterceptor(NetworkInterceptor(context))
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            socketTimeoutMillis = 30000
        }

        install(Auth) {
            bearer {
                loadTokens {
                    val accessToken = tokenManager.getAccessToken()
                    val refreshToken = tokenManager.getRefreshToken()

                    if (accessToken != null && refreshToken != null) {
                        BearerTokens(accessToken, refreshToken)
                    } else {
                        null
                    }
                }

                refreshTokens {
                    try {
                        val refreshToken = tokenManager.getRefreshToken()
                        if (refreshToken != null) {
                            val tempClient = createUnauthenticatedClient()
                            val response: TokenResponse = tempClient.post("$baseUrl/api/auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(RefreshTokenRequest(refreshToken))
                            }.body()

                            tokenManager.saveTokens(response.accessToken, response.refreshToken)
                            BearerTokens(response.accessToken, response.refreshToken)
                        } else {
                            null
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                sendWithoutRequest { request ->
                    !request.url.pathSegments.contains("auth")
                }
            }
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        defaultRequest {
            url(baseUrl)
            accept(ContentType.Application.Json)
        }
    }

    private fun createUnauthenticatedClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
            }
        }
    }

    suspend fun register(request: RegisterRequest): TokenResponse {
        val response: TokenResponse = handleResponse {
            client.post("/api/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

        saveTokens(response.accessToken, response.refreshToken)

        return response
    }

    suspend fun login(request: LoginRequest): TokenResponse {
        val response: TokenResponse = handleResponse {
            client.post("/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }

        saveTokens(response.accessToken, response.refreshToken)

        return response
    }

    suspend fun getUser(userId: String): UserResponse {
        return handleResponse {
            client.get("/api/users/$userId") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getCurrentUser(): UserResponse {
        return handleResponse {
            client.get("/api/users/me") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getUserStats(): UserStatsResponse {
        return handleResponse {
            client.get("/api/users/me/stats") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun updateUser(request: UserUpdateRequest): UserResponse {
        return handleResponse {
            client.patch("/api/users/me") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    suspend fun getAds(params: Map<String, Any?> = emptyMap()): List<AdResponse> {
        return handleResponse {
            client.get("/api/ads/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)

                params.forEach { (key, value) ->
                    value?.let { parameter(key, it) }
                }
            }
        }
    }

    suspend fun getAd(adId: String): AdResponse {
        return handleResponse {
            client.get("/api/ads/$adId") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
            }
        }
    }

    suspend fun getMyAds(): List<AdResponse> {
        return handleResponse {
            client.get("/api/ads/me/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
            }
        }
    }

    suspend fun createAd(request: AdCreateRequest): AdResponse {
        return handleResponse {
            client.post("/api/ads/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
                setBody(request)
            }
        }
    }

    suspend fun updateAd(adId: String, request: AdUpdateRequest): AdResponse {
        return handleResponse {
            client.patch("/api/ads/$adId") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
                setBody(request)
            }
        }
    }

    suspend fun deleteAd(adId: String): Boolean {
        handleResponse<Unit> {
            client.delete("/api/ads/$adId") {
                contentType(ContentType.Application.Json)
            }
        }
        return true
    }

    suspend fun getFavorites(): List<AdResponse> {
        return handleResponse {
            client.get("/api/favorites/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
            }
        }
    }

    suspend fun addFavorite(adId: String): FavoriteResponse {
        return handleResponse {
            client.post("/api/favorites/$adId") {
                contentType(ContentType.Application.Json)
                setBody("{}")
            }
        }
    }

    suspend fun removeFavorite(adId: String): Boolean {
        handleResponse<Unit> {
            client.delete("/api/favorites/$adId") {
                contentType(ContentType.Application.Json)
            }
        }
        return true
    }

    suspend fun getCategories(): List<CategoryResponse> {
        return handleResponse {
            client.get("/api/categories/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
            }
        }
    }

    suspend fun getLocations(): List<LocationResponse> {
        return handleResponse {
            client.get("/api/locations/") {
                contentType(ContentType.Application.Json)
                header("Accept-Language", currentLanguage)
            }
        }
    }

    private suspend inline fun <reified T> handleResponse(
        request: () -> HttpResponse
    ): T {
        val response = request()
        val statusCode = response.status.value

        if (statusCode in 200..299) {
            return response.body<T>()
        } else {
            val errorBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                "{}"
            }

            throw try {
                val errorResponse = Json.decodeFromString<ApiErrorResponse>(errorBody)
                ApiException(
                    message = errorResponse.error.message,
                    statusCode = statusCode,
                    errorCode = errorResponse.error.code
                )
            } catch (e: Exception) {
                ApiException(
                    message = "HTTP $statusCode: ${response.status.description}",
                    statusCode = statusCode
                )
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
}