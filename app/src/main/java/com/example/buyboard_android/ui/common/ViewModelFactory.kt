package com.example.buyboard_android.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buyboard_android.ui.favorites.FavoritesViewModel
import com.example.buyboard_android.ui.home.HomeViewModel
import com.example.buyboard_android.ui.my_ads.MyAdsViewModel
import com.example.buyboard_android.ui.profile.ProfileViewModel
import com.example.buyboard_android.data.network.services.*

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                val adService = AdService(context)
                val categoryService = CategoryService(context)
                val locationService = LocationService(context)
                HomeViewModel(adService, categoryService, locationService) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                val favoriteService = FavoriteService(context)
                FavoritesViewModel(favoriteService) as T
            }
            modelClass.isAssignableFrom(MyAdsViewModel::class.java) -> {
                val adService = AdService(context)
                MyAdsViewModel(adService) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                val userService = UserService(context)
                val adService = AdService(context)
                ProfileViewModel(userService, adService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}