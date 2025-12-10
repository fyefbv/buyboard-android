package com.example.buyboard_android.data.network

class ApiException(
    message: String,
    val statusCode: Int? = null,
    val errorCode: String? = null
) : Exception(message)
