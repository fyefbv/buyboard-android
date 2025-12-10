package com.example.buyboard_android

import android.app.Application
import com.example.buyboard_android.data.network.ApiClient

class BuyBoardApp : Application() {
    val apiClient: ApiClient by lazy {
        ApiClient(applicationContext)
    }
}