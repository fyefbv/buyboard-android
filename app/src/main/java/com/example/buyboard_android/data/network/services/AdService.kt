package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.dto.requests.AdCreateRequest
import com.example.buyboard_android.data.models.dto.requests.AdUpdateRequest
import com.example.buyboard_android.data.models.extensions.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

    suspend fun getAds(params: Map<String, Any?> = emptyMap()): List<Ad> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getAds(params)
                response.toDomain()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getAd(adId: String): Ad {
        val response = apiClient.getAd(adId)
        return response.toDomain()
    }

    suspend fun getMyAds(): List<Ad> {
        val response = apiClient.getMyAds()
        return response.toDomain()
    }

    suspend fun createAd(
        categoryId: String,
        locationId: String,
        price: Int,
        title: String,
        description: String? = null
    ): Ad {
        val request = AdCreateRequest(categoryId, locationId, price, title, description)
        val response = apiClient.createAd(request)
        return response.toDomain()
    }

    suspend fun updateAd(
        adId: String,
        categoryId: String? = null,
        locationId: String? = null,
        price: Int? = null,
        title: String? = null,
        description: String? = null
    ): Ad {
        val request = AdUpdateRequest(categoryId, locationId, price, title, description)
        val response = apiClient.updateAd(adId, request)
        return response.toDomain()
    }

    suspend fun deleteAd(adId: String): Boolean {
        return apiClient.deleteAd(adId)
    }
}