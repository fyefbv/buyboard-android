package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.domain.Location
import com.example.buyboard_android.data.models.extensions.toDomain
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException

class LocationService(private val apiClient: ApiClient) {
    private var cachedLocations: List<Location>? = null

    suspend fun getLocations(forceRefresh: Boolean = false): List<Location> {
        if (!forceRefresh && cachedLocations != null) {
            return cachedLocations!!
        }

        try {
            val response = apiClient.getLocations()
            val locations = response.toDomain()
            cachedLocations = locations
            return locations
        } catch (e: ApiException) {
            cachedLocations = null
            return emptyList()
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