package com.example.buyboard_android.data.models.domain

data class User(
    val id: String,
    val login: String,
    val email: String,
    val avatarUrl: String? = null,
    val createdAt: String,
    val updatedAt: String
)