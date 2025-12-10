package com.example.buyboard_android.data.models.dto.responses

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: String,
    val name: String?
)