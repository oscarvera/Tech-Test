package com.oscarvera.techtest.ui.navigation

import androidx.annotation.StringRes
import com.oscarvera.techtest.R

sealed class Destinations(val route: String, @StringRes val labelRes: Int) {
    data object Products : Destinations("products", R.string.product_screen_title)
    data object Favorites : Destinations("favorites", R.string.favorites_screen_title)
    data object Profile : Destinations("profile", R.string.profile_screen_title)
}
