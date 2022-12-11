package com.aminovic.restaurantorders

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aminovic.restaurantorders.navigation.Screen
import com.aminovic.restaurantorders.presentation.screens.details.DetailsScreen
import com.aminovic.restaurantorders.presentation.screens.orders.OrdersScreen
import com.aminovic.restaurantorders.ui.theme.RestaurantOrdersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantOrdersTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Orders.route
                    ) {
                        composable(Screen.Orders.route) {
                            OrdersScreen(
                                scaffoldState = scaffoldState,
                                onNavigateToDetails = { id ->
                                    navController.navigate(
                                        Screen.Details.route + "/$id"
                                    )
                                }
                            )
                        }
                        composable(
                            route = Screen.Details.route + "/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) {
                            val id = it.arguments?.getInt("id") ?: -1
                            DetailsScreen(id)
                        }
                    }
                }
            }
        }
    }
}
