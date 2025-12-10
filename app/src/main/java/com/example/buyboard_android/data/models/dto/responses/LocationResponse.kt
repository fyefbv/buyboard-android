package com.example.buyboard_android.data.models.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val id: String,
    val name: String?
)