package com.aminovic.restaurantorders

import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.espresso.Espresso
import com.aminovic.restaurantorders.data.local.OrderEntity
import com.aminovic.restaurantorders.domain.use_case.*
import com.aminovic.restaurantorders.navigation.Screen
import com.aminovic.restaurantorders.presentation.screens.details.DetailsScreen
import com.aminovic.restaurantorders.presentation.screens.details.DetailsViewModel
import com.aminovic.restaurantorders.presentation.screens.orders.OrdersScreen
import com.aminovic.restaurantorders.presentation.screens.orders.OrdersViewModel
import com.aminovic.restaurantorders.repository.OrderRepositoryFake
import com.aminovic.restaurantorders.ui.theme.RestaurantOrdersTheme
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.concurrent.schedule


@ExperimentalComposeUiApi
@HiltAndroidTest
class OrdersE2E {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repositoryFake: OrderRepositoryFake
    private lateinit var orderUseCases: OrderUseCases
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var detailsViewModel: DetailsViewModel

    private lateinit var navController: NavHostController

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Before
    fun setUp() {
        repositoryFake = OrderRepositoryFake()
        val removeOrderUseCase = RemoveOrderUseCase(repositoryFake)
        orderUseCases = OrderUseCases(
            addOrderUseCase = AddOrderUseCase(repositoryFake),
            changeOrderStatusUseCase = ChangeOrderStatusUseCase(repositoryFake, removeOrderUseCase),
            getOrdersUseCase = GetOrdersUseCase(repositoryFake),
            getOrderUseCase = GetOrderUseCase(repositoryFake),
            removeOrderUseCase = removeOrderUseCase
        )
        ordersViewModel = OrdersViewModel(
            orderUseCases = orderUseCases
        )
        detailsViewModel = DetailsViewModel(
            orderUseCases = orderUseCases
        )
        composeRule.activity.setContent {
            RestaurantOrdersTheme {
                val scaffoldState = rememberScaffoldState()
                navController = rememberNavController()
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
                                },
                                viewModel = ordersViewModel
                            )
                        }
                        composable(
                            route = Screen.Details.route + "/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val id = it.arguments?.getInt("id")!!
                            DetailsScreen(
                                orderId = id,
                                viewModel = detailsViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    fun ComposeContentTestRule.waitUntilTimeout(
        timeoutMillis: Long
    ) {
        AsyncTimer.start(timeoutMillis)
        this.waitUntil(
            condition = { AsyncTimer.expired },
            timeoutMillis = timeoutMillis + 1000
        )
    }

    object AsyncTimer {
        var expired = false
        fun start(delay: Long = 1000) {
            expired = false
            Timer().schedule(delay = delay) {
                expired = true
            }
        }
    }

    @Test
    fun addOrder_appearsInList() {
        repositoryFake.orders = mutableListOf<OrderEntity>()

        composeRule
            .onNodeWithText("Add order")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Add order")
            .performClick()

        composeRule.waitUntilTimeout(500L)

        composeRule
            .onNodeWithText("new")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("new")
            .performClick()

        composeRule
            .onNodeWithText("preparing")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("preparing")
            .performClick()

        composeRule
            .onNodeWithContentDescription("orderItem")
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("orderItem")
            .performClick()

        assertThat(
            navController
                .currentDestination
                ?.route
                ?.startsWith(Screen.Details.route)
        ).isTrue()

        composeRule.waitUntilTimeout(1000L)

        Espresso.pressBack()

        composeRule
            .onNodeWithText("Add order")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("Add order")
            .performClick()
        composeRule
            .onNodeWithText("Add order")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("Add order")
            .performClick()

        composeRule
            .onNodeWithText("ready")
            .assertIsDisplayed()

        composeRule
            .onNodeWithText("ready")
            .performClick()

        composeRule
            .onNodeWithText("delivered")
            .assertIsDisplayed()

        composeRule.waitUntilTimeout(2000L)
    }
}
