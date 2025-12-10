package com.example.buyboard_android.data.models.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdFilters(
    @SerialName("min_price")
    val minPrice: Int? = null,
    @SerialName("max_price")
    val maxPrice: Int? = null,
    val title: String? = null,
    @SerialName("category_id")
    val categoryId: String? = null,
    @SerialName("location_id")
    val locationId: String? = null,
    @SerialName("ad_ids")
    val adIds: List<String>? = null
)