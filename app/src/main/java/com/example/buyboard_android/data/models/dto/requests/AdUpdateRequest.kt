package com.example.buyboard_android.data.models.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdUpdateRequest(
    @SerialName("category_id")
    val categoryId: String? = null,
    @SerialName("location_id")
    val locationId: String? = null,
    val price: Int? = null,
    val title: String? = null,
    val description: String? = null
)