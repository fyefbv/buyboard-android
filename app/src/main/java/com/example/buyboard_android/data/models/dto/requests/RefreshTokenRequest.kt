package com.example.buyboard_android.data.models.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token: String
)