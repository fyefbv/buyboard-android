package com.example.buyboard_android.data.models.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val login: String? = null,
    val email: String? = null,
    val password: String? = null
)