package com.aminovic.restaurantorders.domain.use_case

import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.domain.mapper.toOrderEntity
import com.aminovic.restaurantorders.domain.repository.OrderRepository

class RemoveOrderUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: Order) {
        repository.deleteOrder(order.toOrderEntity())
    }
}
