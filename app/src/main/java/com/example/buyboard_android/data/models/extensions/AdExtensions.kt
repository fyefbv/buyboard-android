package com.example.buyboard_android.data.models.extensions

import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.dto.responses.AdResponse

fun AdResponse.toDomain(): Ad {
    return Ad(
        id = id,
        userId = userId,
        title = title,
        description = description ?: "",
        price = price.toDouble(),
        category = category.toDomain(),
        location = location.toDomain(),
        images = images,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun List<AdResponse>.toDomain(): List<Ad> {
    return map { it.toDomain() }
}