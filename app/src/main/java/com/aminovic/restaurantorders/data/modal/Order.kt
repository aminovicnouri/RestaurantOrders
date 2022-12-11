package com.aminovic.restaurantorders.data.modal

import java.time.LocalDate

data class Order(
    val id: Int? = null,
    val name: String? = null,
    val date: LocalDate,
    val status: OrderStatus
)
