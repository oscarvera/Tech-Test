package com.oscarvera.techtest

import com.oscarvera.techtest.core.ui.UiState
import com.oscarvera.techtest.domain.model.Product
import com.oscarvera.techtest.domain.usecase.GetProductsUseCase
import com.oscarvera.techtest.domain.usecase.ObserveFavoriteIdsUseCase
import com.oscarvera.techtest.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import com.oscarvera.techtest.ui.products.ProductsViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun sampleProducts(): List<Product> = listOf(
        Product(
            id = 1,
            title = "Product 1",
            price = 10.0,
            description = "desc",
            category = "cat",
            image = "img"
        ),
        Product(
            id = 2,
            title = "Product 2",
            price = 20.0,
            description = "desc",
            category = "cat",
            image = "img"
        )
    )

    /**
     * GIVEN a repository that successfully returns a list of products
     * WHEN the ProductsViewModel is initialized
     * THEN the uiState is UiState.Success containing the loaded products
     */
    @Test
    fun `init loads products and emits Success`() = runTest {
        val repo = FakeProductRepository().apply {
            productsResult = Result.success(sampleProducts())
        }

        val vm = ProductsViewModel(
            getProducts = GetProductsUseCase(repo),
            toggleFavorite = ToggleFavoriteUseCase(repo),
            observeFavoriteIds = ObserveFavoriteIdsUseCase(repo)
        )

        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is UiState.Success)
        state as UiState.Success
        assertEquals(2, state.data.size)
        assertEquals("Product 1", state.data.first().title)
    }

    /**
     * GIVEN a repository that throws an exception when fetching products
     * WHEN the ProductsViewModel is initialized
     * THEN the uiState is UiState.Error with the expected error message
     */
    @Test
    fun `init emits Error when repository throws`() = runTest {
        val repo = FakeProductRepository().apply {
            productsResult = Result.failure(IllegalStateException("boom"))
        }

        val vm = ProductsViewModel(
            getProducts = GetProductsUseCase(repo),
            toggleFavorite = ToggleFavoriteUseCase(repo),
            observeFavoriteIds = ObserveFavoriteIdsUseCase(repo)
        )

        advanceUntilIdle()

        val state = vm.uiState.value
        assertTrue(state is UiState.Error)
        state as UiState.Error
        assertTrue(state.message.contains("boom"))
    }

    /**
     * GIVEN a ViewModel with no favorite products
     * WHEN a product is toggled as favorite
     * THEN the product id is added to the favorites set
     * WHEN the same product is toggled again
     * THEN the product id is removed from the favorites set
     */
    @Test
    fun `toggle favorite updates favoriteIds`() = runTest {
        val repo = FakeProductRepository().apply {
            productsResult = Result.success(sampleProducts())
        }

        val vm = ProductsViewModel(
            getProducts = GetProductsUseCase(repo),
            toggleFavorite = ToggleFavoriteUseCase(repo),
            observeFavoriteIds = ObserveFavoriteIdsUseCase(repo)
        )

        advanceUntilIdle()

        val p1 = sampleProducts().first()

        assertTrue(vm.favoriteIds.value.isEmpty())

        vm.onFavoriteClick(p1)
        advanceUntilIdle()
        assertTrue(vm.favoriteIds.value.contains(1))

        vm.onFavoriteClick(p1)
        advanceUntilIdle()
        assertTrue(vm.favoriteIds.value.isEmpty())
    }
}
