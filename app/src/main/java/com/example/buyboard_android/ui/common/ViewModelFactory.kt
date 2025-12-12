package com.example.buyboard_android.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buyboard_android.BuyBoardApp
import com.example.buyboard_android.ui.favorites.FavoritesViewModel
import com.example.buyboard_android.ui.home.HomeViewModel
import com.example.buyboard_android.ui.my_ads.MyAdsViewModel
import com.example.buyboard_android.ui.profile.ProfileViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val app = context.applicationContext as BuyBoardApp

        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(app.adService, app.categoryService, app.locationService) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(app.favoriteService) as T
            }
            modelClass.isAssignableFrom(MyAdsViewModel::class.java) -> {
                MyAdsViewModel(app.adService) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(app.userService, app.adService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}