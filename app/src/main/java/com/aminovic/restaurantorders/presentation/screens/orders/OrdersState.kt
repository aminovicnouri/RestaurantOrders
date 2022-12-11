package com.aminovic.restaurantorders.presentation.screens.orders

import com.aminovic.restaurantorders.data.modal.Order


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

data class OrdersState(
    val ordersList: List<Order> = emptyList(),
    val isLoading: Boolean = false
)