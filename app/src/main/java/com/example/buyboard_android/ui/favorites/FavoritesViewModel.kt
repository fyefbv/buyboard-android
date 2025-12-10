package com.example.buyboard_android.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.network.services.FavoriteService
import com.example.buyboard_android.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoriteService: FavoriteService
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<ViewState<List<Ad>>>(ViewState.Empty)
    val favoritesState: StateFlow<ViewState<List<Ad>>> = _favoritesState.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _favoritesState.value = ViewState.Loading

            try {
                val favorites = favoriteService.getFavorites()
                _favoritesState.value = if (favorites.isEmpty()) {
                    ViewState.Empty
                } else {
                    ViewState.Success(favorites)
                }
            } catch (e: Exception) {
                _favoritesState.value = ViewState.Error("Ошибка загрузки избранного")
            }
        }
    }

    suspend fun toggleFavorite(adId: String, isCurrentlyFavorite: Boolean): Boolean {
        return favoriteService.toggleFavorite(adId, isCurrentlyFavorite)
    }

    fun refresh() {
        loadFavorites()
    }
}