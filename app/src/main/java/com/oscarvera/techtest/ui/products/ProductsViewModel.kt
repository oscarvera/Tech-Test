package com.oscarvera.techtest.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oscarvera.techtest.core.ui.UiState
import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.usecase.GetProductsUseCase
import com.oscarvera.techtest.domain.usecase.ObserveFavoriteIdsUseCase
import com.oscarvera.techtest.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProducts: GetProductsUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    observeFavoriteIds: ObserveFavoriteIdsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    val favoriteIds: StateFlow<Set<Int>> = observeFavoriteIds()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    init {
        load()
    }

    fun load(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val products = getProducts()
                _uiState.value = UiState.Success(products)
            } catch (t: Throwable) {
                _uiState.value = UiState.Error(t.message ?: "Error loading products")
            }
        }
    }

    fun onFavoriteClick(product: Product) {
        viewModelScope.launch {
            toggleFavorite(product)
        }
    }


}