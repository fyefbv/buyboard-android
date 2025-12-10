package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.domain.Location
import com.example.buyboard_android.data.models.extensions.toDomain

class LocationService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

    private var cachedLocations: List<Location>? = null

    suspend fun getLocations(forceRefresh: Boolean = false): List<Location> {
        return if (!forceRefresh && cachedLocations != null) {
            cachedLocations!!
        } else {
            val response = apiClient.getLocations()
            val locations = response.toDomain()
            cachedLocations = locations
            locations
        }
    }

    suspend fun getLocationById(id: String): Location? {
        val locations = getLocations()
        return locations.find { it.id == id }
    }

    fun clearCache() {
        cachedLocations = null
    }
}