package com.aminovic.restaurantorders.presentation.screens.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminovic.restaurantorders.R
import com.aminovic.restaurantorders.domain.use_case.AddOrderUseCase
import com.aminovic.restaurantorders.domain.use_case.ChangeOrderStatusUseCase
import com.aminovic.restaurantorders.domain.use_case.GetOrdersUseCase
import com.aminovic.restaurantorders.util.UiEvent
import com.aminovic.restaurantorders.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val addOrderUseCase: AddOrderUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val changeOrderStatusUseCase: ChangeOrderStatusUseCase,
) : ViewModel() {

    var state by mutableStateOf(OrdersState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getOrdersJob: Job? = null

    init {
        getOrders()
    }

    fun onEvent(event: OrdersEvent) {
        when (event) {
            is OrdersEvent.AddOrder -> {
                viewModelScope.launch {
                    addOrderUseCase()
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(message = UiText.StringResource(resId = R.string.order_added))
                    )
                    _uiEvent.send(
                        UiEvent.ScrollToBottom(position = state.ordersList.size)
                    )
                }
            }
            is OrdersEvent.ChangeStatus -> {
                viewModelScope.launch() {
                    changeOrderStatusUseCase(event.order)
                }
            }
            else -> Unit
        }
    }

    private fun getOrders() {
        getOrdersJob?.cancel()
        getOrdersJob = getOrdersUseCase()
            .onEach { list ->
                state = state.copy(
                    ordersList = list
                )
            }
            .launchIn(viewModelScope)
    }
}