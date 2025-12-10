package com.example.buyboard_android.data.models.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdCreateRequest(
    @SerialName("category_id")
    val categoryId: String,
    @SerialName("location_id")
    val locationId: String,
    val price: Int,
    val title: String,
    val description: String? = null
)