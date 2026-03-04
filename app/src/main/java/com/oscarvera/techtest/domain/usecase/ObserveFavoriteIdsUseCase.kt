package com.oscarvera.techtest.domain.usecase

import com.oscarvera.techtest.domain.repository.ProductRepository

class ObserveFavoriteIdsUseCase(private val repository: ProductRepository) {
    operator fun invoke() = repository.observeFavoriteIds()
}