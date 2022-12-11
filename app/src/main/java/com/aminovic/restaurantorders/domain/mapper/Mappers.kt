package com.aminovic.restaurantorders.domain.mapper

import com.aminovic.restaurantorders.data.local.OrderEntity
import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.data.modal.OrderStatus
import java.time.LocalDate

//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

fun Order.toOrderEntity() = OrderEntity(
    id = this.id,
    dayOfMonth = this.date.dayOfMonth,
    month = this.date.monthValue,
    year = this.date.year,
    this.status.value
)

fun OrderEntity.toOrder() = Order(
    id = this.id,
    name = "Order-${this.id}",
    status = OrderStatus.getOrderStatusFromValue(this.status),
    date = LocalDate.of(this.year, this.month, this.dayOfMonth)
)