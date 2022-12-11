package com.aminovic.restaurantorders.presentation.screens.orders

import com.aminovic.restaurantorders.data.modal.Order


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

sealed class OrdersEvent {
    object AddOrder : OrdersEvent()
    data class ChangeStatus(val order: Order) : OrdersEvent()
}