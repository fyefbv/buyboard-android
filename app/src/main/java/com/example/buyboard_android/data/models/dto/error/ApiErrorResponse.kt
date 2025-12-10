package com.example.buyboard_android.data.models.dto.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String,
    val timestamp: String
)

@Serializable
data class ApiErrorResponse(
    val success: Boolean = false,
    val error: ErrorResponse
)