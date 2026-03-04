package com.oscarvera.techtest.domain.usecase

import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.repository.ProductRepository

class ToggleFavoriteUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) = repository.toggleFavorite(product)
}