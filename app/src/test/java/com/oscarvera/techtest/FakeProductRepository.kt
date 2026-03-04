package com.oscarvera.techtest

import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.model.User
import com.oscarvera.techtest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeProductRepository : ProductRepository {

    var productsResult: Result<List<Product>> = Result.success(emptyList())
    var userResult: Result<User> = Result.success(User(id = 8, email = "a@b.com", username = "user"))

    private val favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    private val favorites = MutableStateFlow<List<Product>>(emptyList())

    override suspend fun getProducts(): List<Product> =
        productsResult.getOrThrow()

    override suspend fun getUserProfile(userId: Int): User =
        userResult.getOrThrow()

    override fun observeFavoriteIds(): Flow<Set<Int>> = favoriteIds

    override suspend fun toggleFavorite(product: Product) {
        val currentIds = favoriteIds.value
        val nowFav = !currentIds.contains(product.id)

        favoriteIds.value = if (nowFav) currentIds + product.id else currentIds - product.id

        val currentFavs = favorites.value
        favorites.value = if (nowFav) currentFavs + product else currentFavs.filterNot { it.id == product.id }
    }

    override fun observeFavoriteCount(): Flow<Int> =
        favoriteIds.map { it.size }

    override fun observeFavorites(): Flow<List<Product>> = favorites
}
