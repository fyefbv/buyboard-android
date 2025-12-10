package com.example.buyboard_android.data.models.dto.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdResponse(
    val id: String,
    @SerialName("user_id")
    val userId: String,
    val category: CategoryResponse,
    val location: LocationResponse,
    val price: Int,
    val title: String,
    val description: String? = null,
    val images: List<String>,
    @SerialName("is_favorite")
    val isFavorite: Boolean,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)