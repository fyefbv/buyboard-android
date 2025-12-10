package com.example.buyboard_android.data.models.extensions

import com.example.buyboard_android.data.models.domain.Location
import com.example.buyboard_android.data.models.dto.responses.LocationResponse

fun LocationResponse.toDomain(): Location {
    return Location(
        id = id,
        name = name ?: "Не указано"
    )
}

fun List<LocationResponse>.toDomain(): List<Location> {
    return map { it.toDomain() }
}