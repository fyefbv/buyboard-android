package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.extensions.toDomain

class FavoriteService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

    suspend fun getFavorites(): List<Ad> {
        val response = apiClient.getFavorites()
        return response.toDomain()
    }

    suspend fun addFavorite(adId: String): Boolean {
        return try {
            apiClient.addFavorite(adId)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun removeFavorite(adId: String): Boolean {
        return try {
            apiClient.removeFavorite(adId)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun toggleFavorite(adId: String, isCurrentlyFavorite: Boolean): Boolean {
        return if (isCurrentlyFavorite) {
            removeFavorite(adId)
            false
        } else {
            addFavorite(adId)
            true
        }
    }
}