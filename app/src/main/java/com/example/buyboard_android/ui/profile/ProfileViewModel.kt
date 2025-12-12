package com.example.buyboard_android.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyboard_android.data.models.domain.User
import com.example.buyboard_android.data.network.ApiException
import com.example.buyboard_android.data.network.services.AdService
import com.example.buyboard_android.data.network.services.UserService
import com.example.buyboard_android.ui.common.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userService: UserService,
    private val adService: AdService
) : ViewModel() {

    private val _profileState = MutableStateFlow<ViewState<User>>(ViewState.Empty)
    val profileState: StateFlow<ViewState<User>> = _profileState.asStateFlow()

    private val _statsState = MutableStateFlow(0)
    val statsState: StateFlow<Int> = _statsState.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _profileState.value = ViewState.Loading

            try {
                val user = userService.getCurrentUser()
                _profileState.value = ViewState.Success(user)

                val stats = userService.getUserStats()
                _statsState.value = stats
            } catch (e: ApiException) {
                _profileState.value = ViewState.Error("Ошибка загрузки профиля")
            }
        }
    }

    suspend fun updateProfile(login: String? = null, email: String? = null): Boolean {
        return try {
            val updatedUser = userService.updateUser(login, email)
            _profileState.value = ViewState.Success(updatedUser)
            true
        } catch (e: ApiException) {
            false
        }
    }

    fun refresh() {
        loadProfile()
    }
}