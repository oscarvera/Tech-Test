package com.oscarvera.techtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oscarvera.techtest.data.local.db.AppDatabase
import com.oscarvera.techtest.data.remote.FakeStoreApi
import com.oscarvera.techtest.data.remote.dto.ProductDto
import com.oscarvera.techtest.data.remote.dto.UserDto
import com.oscarvera.techtest.data.repository.ProductRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryIntegrationTest {

    private lateinit var db: AppDatabase
    private lateinit var api: FakeStoreApi
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        api = FakeStoreApiFake()

        repository = ProductRepositoryImpl(
            api = api,
            favoriteDao = db.favoriteDao()
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    /*
    GIVEN a repository with a fake API returning 2 products
    AND an empty in-memory Room database
    WHEN getProducts() is called
    THEN the repository returns a list of domain Products
    AND the data is correctly mapped from DTO to domain
    */

    @Test
    fun getProducts_maps_dto_to_domain() = runTest {
        val products = repository.getProducts()

        assertEquals(2, products.size)
        assertEquals(1, products[0].id)
        assertEquals("Backpack", products[0].title)
        assertEquals(109.95, products[0].price, 0.0001)
    }

    /*
    GIVEN a repository with a fake API returning 2 products
    AND an empty in-memory Room database
    WHEN toggleFavorite(product) is called for a non-favorite product
    THEN the product is stored in Room
    AND the favorite flows emit updated values
    WHEN toggleFavorite(product) is called again
    THEN the product is removed from Room
    AND all favorite flows emit empty values
     */

    @Test
    fun toggleFavorite_persists_in_room_and_updates_flows() = runTest {
        val product = repository.getProducts().first()

        assertEquals(0, repository.observeFavoriteCount().first())
        assertFalse(repository.observeFavoriteIds().first().contains(product.id))

        //Save on favorites
        repository.toggleFavorite(product)

        val idsAfterInsert = repository.observeFavoriteIds().first()
        val countAfterInsert = repository.observeFavoriteCount().first()
        val favsAfterInsert = repository.observeFavorites().first()

        assertTrue(idsAfterInsert.contains(product.id))
        assertEquals(1, countAfterInsert)
        assertEquals(1, favsAfterInsert.size)
        assertEquals(product.id, favsAfterInsert.first().id)

        // Delete from favorites
        repository.toggleFavorite(product)

        val idsAfterDelete = repository.observeFavoriteIds().first()
        val countAfterDelete = repository.observeFavoriteCount().first()
        val favsAfterDelete = repository.observeFavorites().first()

        assertFalse(idsAfterDelete.contains(product.id))
        assertEquals(0, countAfterDelete)
        assertTrue(favsAfterDelete.isEmpty())
    }


    private class FakeStoreApiFake : FakeStoreApi {
        override suspend fun getProducts(): List<ProductDto> = listOf(
            ProductDto(
                id = 1,
                title = "Backpack",
                price = 109.95,
                description = "A backpack",
                category = "men's clothing",
                image = "https://example.com/1.png"
            ),
            ProductDto(
                id = 2,
                title = "T-Shirt",
                price = 22.3,
                description = "A t-shirt",
                category = "men's clothing",
                image = "https://example.com/2.png"
            )
        )

        override suspend fun getUser(id: Int): UserDto =
            UserDto(id = id, email = "user$id@mail.com", username = "user$id")
    }
}
