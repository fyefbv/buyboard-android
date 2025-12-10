package com.example.buyboard_android.data.models.domain

data class Ad(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: Category,
    val location: Location,
    val images: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val createdAt: String,
    val updatedAt: String
)