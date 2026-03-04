package com.oscarvera.techtest.domain.repository

import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getUserProfile(userId: Int): User

    fun observeFavoriteIds(): Flow<Set<Int>>

    suspend fun toggleFavorite(product: Product)

    fun observeFavoriteCount(): Flow<Int>

    fun observeFavorites(): Flow<List<Product>>

}