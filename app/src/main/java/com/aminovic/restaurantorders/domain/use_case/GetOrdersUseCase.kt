package com.aminovic.restaurantorders.domain.use_case

import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.domain.mapper.toOrder
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetOrdersUseCase(
    private val repository: OrderRepository
) {
    operator fun invoke(): Flow<List<Order>> {
        return repository.getOrders().map { list ->
            list.map { it.toOrder() }
        }
    }
}

