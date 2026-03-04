package com.oscarvera.techtest.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscarvera.techtest.core.ui.UiState
import com.oscarvera.techtest.domain.model.User
import com.oscarvera.techtest.domain.usecase.GetFavoriteCountUseCase
import com.oscarvera.techtest.domain.usecase.GetUserProfileUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfile: GetUserProfileUseCase,
    getFavoriteCount: GetFavoriteCountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState.asStateFlow()

    val favoriteCount: StateFlow<Int> = getFavoriteCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    init {
        load(userId = 8)
    }

    fun load(userId: Int = 8) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val user = getUserProfile(userId)
                _uiState.value = UiState.Success(user)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t.message ?: "Error cargando perfil")
            }
        }
    }
}
