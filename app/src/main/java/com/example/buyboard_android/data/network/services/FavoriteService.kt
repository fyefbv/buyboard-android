package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.extensions.toDomain
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteService(private val apiClient: ApiClient) {
    suspend fun getFavorites(): List<Ad> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiClient.getFavorites()
                response.toDomain()
            } catch (e: ApiException) {
                emptyList()
            }
        }
    }

    suspend fun addFavorite(adId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                apiClient.addFavorite(adId)
                true
            } catch (e: ApiException) {
                false
            }
        }
    }

    suspend fun removeFavorite(adId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                apiClient.removeFavorite(adId)
                true
            } catch (e: ApiException) {
                false
            }
        }
    }

    suspend fun toggleFavorite(adId: String, isCurrentlyFavorite: Boolean): Boolean {
        return try {
            if (isCurrentlyFavorite) {
                removeFavorite(adId)
                false
            } else {
                addFavorite(adId)
                true
            }
        } catch (e: ApiException) {
            isCurrentlyFavorite
        }
    }
}