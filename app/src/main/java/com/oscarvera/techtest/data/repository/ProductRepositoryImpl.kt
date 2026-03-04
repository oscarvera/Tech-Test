package com.oscarvera.techtest.data.repository

import com.oscarvera.techtest.data.local.dao.FavoriteDao
import com.oscarvera.techtest.data.mapper.toDomain
import com.oscarvera.techtest.data.mapper.toDomainProduct
import com.oscarvera.techtest.data.mapper.toFavoriteEntity
import com.oscarvera.techtest.data.remote.FakeStoreApi
import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.model.User
import com.oscarvera.techtest.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val api: FakeStoreApi,
    private val favoriteDao: FavoriteDao
) : ProductRepository {

    private val favoriteIds = MutableStateFlow<Set<Int>>(emptySet())

    override suspend fun getProducts(): List<Product> =
        api.getProducts().map { it.toDomain() }

    override suspend fun getUserProfile(userId: Int): User =
        api.getUser(userId).toDomain()

    override fun observeFavoriteIds(): Flow<Set<Int>> =
        favoriteDao.observeFavoriteIds().map { it.toSet() }

    override suspend fun toggleFavorite(product: Product) {
        val isFav = favoriteDao.isFavorite(product.id)
        if (isFav) {
            favoriteDao.deleteByProductId(product.id)
        } else {
            favoriteDao.upsert(product.toFavoriteEntity())
        }
    }

    override fun observeFavoriteCount(): Flow<Int> =
        favoriteDao.observeFavoriteCount()

    override fun observeFavorites(): Flow<List<Product>> =
        favoriteDao.observeFavorites().map { list ->
            list.map { it.toDomainProduct() }
        }
}