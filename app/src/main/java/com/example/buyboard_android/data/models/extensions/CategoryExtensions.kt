package com.example.buyboard_android.data.models.extensions

import com.example.buyboard_android.data.models.domain.Category
import com.example.buyboard_android.data.models.dto.responses.CategoryResponse

fun CategoryResponse.toDomain(): Category {
    return Category(
        id = id,
        name = name ?: "Без названия"
    )
}

fun List<CategoryResponse>.toDomain(): List<Category> {
    return map { it.toDomain() }
}