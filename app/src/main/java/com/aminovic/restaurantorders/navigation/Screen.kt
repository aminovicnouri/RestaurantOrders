package com.aminovic.restaurantorders.navigation

sealed class Screen(val route: String) {
    object Orders : Screen("orders_screen")
    object Details : Screen("details_screen")
}