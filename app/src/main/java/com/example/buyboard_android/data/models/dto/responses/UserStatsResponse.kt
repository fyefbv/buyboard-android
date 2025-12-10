package com.example.buyboard_android.data.models.dto.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatsResponse(
    @SerialName("active_ads_count")
    val activeAdsCount: Int
)