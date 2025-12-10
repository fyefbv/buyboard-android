package com.example.buyboard_android.data.models.extensions

import com.example.buyboard_android.data.models.domain.User
import com.example.buyboard_android.data.models.dto.responses.UserResponse

fun UserResponse.toDomain(): User {
    return User(
        id = id,
        login = login,
        email = email,
        avatarUrl = avatarUrl,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}