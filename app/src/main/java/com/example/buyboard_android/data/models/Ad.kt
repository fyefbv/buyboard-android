package com.example.buyboard_android.data.models

import java.util.Date

data class Ad(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val images: List<String> = emptyList(),
    val location: String,
    val date: String,
    val isFavorite: Boolean = false,
    val sellerId: String,
    val sellerName: String,
    val condition: String = "NEW"
)
