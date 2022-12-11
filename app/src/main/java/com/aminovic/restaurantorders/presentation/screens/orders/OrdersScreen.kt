package com.aminovic.restaurantorders.presentation.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.aminovic.restaurantorders.R
import com.aminovic.restaurantorders.presentation.components.AddButton
import com.aminovic.restaurantorders.presentation.components.OrderItem
import com.aminovic.restaurantorders.ui.theme.LocalSpacing
import com.aminovic.restaurantorders.util.UiEvent
import kotlinx.coroutines.launch


@Composable
fun OrdersScreen(
    scaffoldState: ScaffoldState,
    onNavigateToDetails: (Int) -> Unit,
    viewModel: OrdersViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ScrollToBottom -> {
                    coroutineScope.launch {
                        listState.scrollToItem(index = event.position)
                    }
                }
                is UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        if (scaffoldState.snackbarHostState.currentSnackbarData == null) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = event.message.asString(context),
                            )
                        }
                    }

                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = spacing.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        AddButton(
            text = stringResource(id = R.string.add),
            onClick = {
                viewModel.onEvent(OrdersEvent.AddOrder)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = listState
        ) {
            items(state.ordersList, key = { it.id!! }) { order ->
                OrderItem(
                    order = order,
                    clickItem = { onNavigateToDetails(order.id!!) },
                    changeStatus = {
                        viewModel.onEvent(OrdersEvent.ChangeStatus(order))
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
    }
}
