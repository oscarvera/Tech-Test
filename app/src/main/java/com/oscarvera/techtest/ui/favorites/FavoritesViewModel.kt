package com.oscarvera.techtest.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscarvera.techtest.core.ui.UiState
import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.usecase.GetFavoritesUseCase
import com.oscarvera.techtest.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavorites: GetFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    init {
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                getFavorites().collect { favorites ->
                    _uiState.value = UiState.Success(favorites)
                }
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t.message ?: "Error cargando favoritos")
            }
        }
    }

    fun onRemoveFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavorite(product)
        }
    }
}