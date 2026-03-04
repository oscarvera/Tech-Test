package com.oscarvera.techtest.domain.usecase

import com.oscarvera.techtest.domain.model.User
import com.oscarvera.techtest.domain.repository.ProductRepository

class GetUserProfileUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(userId: Int): User = repository.getUserProfile(userId)
}