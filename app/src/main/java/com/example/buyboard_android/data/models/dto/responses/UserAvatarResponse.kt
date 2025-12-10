package com.example.buyboard_android.data.models.dto.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAvatarResponse(
    @SerialName("avatar_url")
    val avatarUrl: String?
)