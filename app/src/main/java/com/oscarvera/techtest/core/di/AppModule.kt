package com.oscarvera.techtest.core.di

import com.oscarvera.techtest.data.repository.ProductRepositoryImpl
import com.oscarvera.techtest.domain.repository.ProductRepository
import com.oscarvera.techtest.domain.usecase.GetFavoriteCountUseCase
import com.oscarvera.techtest.domain.usecase.GetFavoritesUseCase
import com.oscarvera.techtest.domain.usecase.GetProductsUseCase
import com.oscarvera.techtest.domain.usecase.GetUserProfileUseCase
import com.oscarvera.techtest.domain.usecase.ObserveFavoriteIdsUseCase
import com.oscarvera.techtest.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val appModule = module {

    single<ProductRepository> {
        ProductRepositoryImpl(
            api = get(),
            favoriteDao = get()
        )
    }

    factory { GetProductsUseCase(repository = get()) }
    factory { ToggleFavoriteUseCase(repository = get()) }
    factory { GetUserProfileUseCase(repository = get()) }
    factory { GetFavoritesUseCase(repository = get()) }
    factory { GetFavoriteCountUseCase(repository = get()) }
    factory { ObserveFavoriteIdsUseCase(repository = get()) }
}