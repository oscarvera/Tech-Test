package com.oscarvera.techtest.domain.usecase

import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = repository.observeFavorites()
}