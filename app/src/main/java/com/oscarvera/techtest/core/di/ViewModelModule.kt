package com.oscarvera.techtest.core.di

import com.oscarvera.techtest.ui.favorites.FavoritesViewModel
import com.oscarvera.techtest.ui.products.ProductsViewModel
import com.oscarvera.techtest.ui.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        ProductsViewModel(
            getProducts = get(),
            toggleFavorite = get(),
            observeFavoriteIds = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            getFavorites = get(),
            toggleFavorite = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getUserProfile = get(),
            getFavoriteCount = get()
        )
    }

}