package com.example.buyboard_android

import android.app.Application
import com.example.buyboard_android.data.network.ApiClient
import com.example.buyboard_android.data.network.TokenManager
import com.example.buyboard_android.data.network.services.AdService
import com.example.buyboard_android.data.network.services.AuthService
import com.example.buyboard_android.data.network.services.CategoryService
import com.example.buyboard_android.data.network.services.FavoriteService
import com.example.buyboard_android.data.network.services.LocationService
import com.example.buyboard_android.data.network.services.UserService

class BuyBoardApp : Application() {
    lateinit var apiClient: ApiClient
    lateinit var tokenManager: TokenManager

    lateinit var authService: AuthService
    lateinit var adService: AdService
    lateinit var userService: UserService
    lateinit var favoriteService: FavoriteService
    lateinit var categoryService: CategoryService
    lateinit var locationService: LocationService

    override fun onCreate() {
        super.onCreate()

        tokenManager = TokenManager(this)
        apiClient = ApiClient(this, tokenManager)

        authService = AuthService(apiClient)
        adService = AdService(apiClient)
        userService = UserService(apiClient)
        favoriteService = FavoriteService(apiClient)
        categoryService = CategoryService(apiClient)
        locationService = LocationService(apiClient)
    }
}