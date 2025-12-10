package com.example.buyboard_android.data.network.services

import android.content.Context
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.data.models.domain.Category
import com.example.buyboard_android.data.models.extensions.toDomain

class CategoryService(private val context: Context) {
    private val apiClient get() = (context.applicationContext as BuyBoardApp).apiClient

    private var cachedCategories: List<Category>? = null

    suspend fun getCategories(forceRefresh: Boolean = false): List<Category> {
        return if (!forceRefresh && cachedCategories != null) {
            cachedCategories!!
        } else {
            val response = apiClient.getCategories()
            val categories = response.toDomain()
            cachedCategories = categories
            categories
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