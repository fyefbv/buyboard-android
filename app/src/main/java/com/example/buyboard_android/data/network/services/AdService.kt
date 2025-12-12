package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.dto.requests.AdCreateRequest
import com.example.buyboard_android.data.models.dto.requests.AdUpdateRequest
import com.example.buyboard_android.data.models.extensions.toDomain
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdService(private val apiClient: ApiClient) {
    suspend fun getAds(params: Map<String, Any?> = emptyMap()): List<Ad> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAds(params)
                response.toDomain()
            } catch (e: ApiException) {
                emptyList()
            }
        }
    }

    suspend fun getAd(adId: String): Ad {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAd(adId)
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка загрузки объявления")
            }
        }
    }

    suspend fun getMyAds(): List<Ad> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getMyAds()
                response.toDomain()
            } catch (e: ApiException) {
                emptyList()
            }
        }
    }

    suspend fun createAd(categoryId: String, locationId: String, price: Int, title: String, description: String? = null): Ad {
        return withContext(Dispatchers.IO) {
            try {
                val request = AdCreateRequest(categoryId, locationId, price, title, description)
                val response = apiClient.createAd(request)
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка создания объявления")
            }
        }
    }

    suspend fun updateAd(adId: String, categoryId: String? = null, locationId: String? = null, price: Int? = null, title: String? = null, description: String? = null): Ad {
        return withContext(Dispatchers.IO) {
            try {
                val request = AdUpdateRequest(categoryId, locationId, price, title, description)
                val response = apiClient.updateAd(adId, request)
                response.toDomain()
            } catch (e: ApiException) {
                throw Exception("Ошибка обновления объявления")
            }
        }
    }

    suspend fun deleteAd(adId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                apiClient.deleteAd(adId)
                true
            } catch (e: ApiException) {
                false
            }
        }
    }
}