package com.example.buyboard_android.data.network.services

import com.example.buyboard_android.data.models.domain.Category
import com.example.buyboard_android.data.models.extensions.toDomain
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.ApiException

class CategoryService(private val apiClient: ApiClient) {
    private var cachedCategories: List<Category>? = null

    suspend fun getCategories(forceRefresh: Boolean = false): List<Category> {
        if (!forceRefresh && cachedCategories != null) {
            return cachedCategories!!
        }

        try {
            val response = apiClient.getCategories()
            val categories = response.toDomain()
            cachedCategories = categories
            return categories
        } catch (e: ApiException) {
            cachedCategories = null
            return emptyList()
        }
    }

    suspend fun getCategoryById(id: String): Category? {
        val categories = getCategories()
        return categories.find { it.id == id }
    }

    fun clearCache() {
        cachedCategories = null
    }
}