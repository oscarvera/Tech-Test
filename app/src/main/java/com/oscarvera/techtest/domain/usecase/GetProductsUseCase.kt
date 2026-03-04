package com.oscarvera.techtest.domain.usecase

import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getProducts()
}