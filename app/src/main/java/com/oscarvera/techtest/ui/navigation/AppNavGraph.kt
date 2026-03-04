package com.oscarvera.techtest.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oscarvera.techtest.R
import com.oscarvera.techtest.ui.favorites.FavoritesScreen
import com.oscarvera.techtest.ui.products.ProductsScreen
import com.oscarvera.techtest.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  val title = when (currentRoute) {
                    Destinations.Products.route -> stringResource(R.string.product_screen_title)
                    Destinations.Favorites.route -> stringResource(R.string.favorites_screen_title)
                    Destinations.Profile.route -> stringResource(R.string.profile_screen_title)
                    else -> "App Name"
                }
                    Text(title)
                }
            )
        },
        bottomBar = { AppBottomBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.Products.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destinations.Products.route) {
                ProductsScreen()
            }

            composable(Destinations.Favorites.route) {
                FavoritesScreen()
            }

            composable(Destinations.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
