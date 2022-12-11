package com.aminovic.restaurantorders.domain.use_case

import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.domain.mapper.toOrder
import com.aminovic.restaurantorders.domain.repository.OrderRepository

class GetOrderUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(id: Int): Order {
        return repository.getOrder(id).toOrder()
    }
}

