package com.oscarvera.techtest.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.oscarvera.techtest.domain.repository.ProductRepository

class GetFavoriteCountUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<Int> = repository.observeFavoriteCount()
}