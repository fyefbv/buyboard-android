package com.example.buyboard_android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyboard_android.data.models.domain.Ad
import com.example.buyboard_android.data.models.domain.Category
import com.example.buyboard_android.data.models.domain.Location
import com.example.buyboard_android.data.network.ApiException
import com.example.buyboard_android.data.network.services.AdService
import com.example.buyboard_android.data.network.services.CategoryService
import com.example.buyboard_android.data.network.services.LocationService
import com.example.buyboard_android.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val adService: AdService,
    private val categoryService: CategoryService,
    private val locationService: LocationService
) : ViewModel() {

    private val _adsState = MutableStateFlow<ViewState<List<Ad>>>(ViewState.Empty)
    val adsState: StateFlow<ViewState<List<Ad>>> = _adsState.asStateFlow()

    private val _categoriesState = MutableStateFlow<List<Category>>(emptyList())
    val categoriesState: StateFlow<List<Category>> = _categoriesState.asStateFlow()

    private val _locationsState = MutableStateFlow<List<Location>>(emptyList())
    val locationsState: StateFlow<List<Location>> = _locationsState.asStateFlow()

    private var currentFilters: Map<String, Any?> = emptyMap()

    fun loadAds(params: Map<String, Any?> = emptyMap()) {
        viewModelScope.launch {
            _adsState.value = ViewState.Loading
            currentFilters = params

            try {
                val ads = adService.getAds(params)
                _adsState.value = if (ads.isEmpty()) ViewState.Empty else ViewState.Success(ads)
            } catch (e: ApiException) {
                _adsState.value = ViewState.Error("Ошибка загрузки: ${e.message}")
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = categoryService.getCategories()
                _categoriesState.value = categories
            } catch (e: ApiException) { }
        }
    }

    fun loadLocations() {
        viewModelScope.launch {
            try {
                val locations = locationService.getLocations()
                _locationsState.value = locations
            } catch (e: ApiException) { }
        }
    }

    fun applyFilters(categoryId: String? = null, locationId: String? = null, minPrice: Int? = null, maxPrice: Int? = null) {
        val params = mutableMapOf<String, Any?>()
        categoryId?.let { params["category_id"] = it }
        locationId?.let { params["location_id"] = it }
        minPrice?.let { params["min_price"] = it }
        maxPrice?.let { params["max_price"] = it }
        loadAds(params)
    }

    fun refresh() {
        loadAds(currentFilters)
        loadCategories()
        loadLocations()
    }
}