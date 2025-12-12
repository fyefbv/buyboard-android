package com.example.buyboard_android.ui.my_ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.network.ApiException
import com.example.buyboard_android.data.network.services.AdService
import com.example.buyboard_android.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyAdsViewModel(
    private val adService: AdService
) : ViewModel() {

    private val _myAdsState = MutableStateFlow<ViewState<List<Ad>>>(ViewState.Empty)
    val myAdsState: StateFlow<ViewState<List<Ad>>> = _myAdsState.asStateFlow()

    fun loadMyAds() {
        viewModelScope.launch {
            _myAdsState.value = ViewState.Loading

            try {
                val ads = adService.getMyAds()
                _myAdsState.value = if (ads.isEmpty()) ViewState.Empty else ViewState.Success(ads)
            } catch (e: ApiException) {
                _myAdsState.value = ViewState.Error("Ошибка загрузки ваших объявлений")
            }
        }
    }

    suspend fun deleteAd(adId: String): Boolean {
        return try {
            val success = adService.deleteAd(adId)
            if (success) loadMyAds()
            success
        } catch (e: ApiException) {
            false
        }
    }

    fun refresh() {
        loadMyAds()
    }
}