package com.aminovic.restaurantorders.domain.use_case

import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.data.modal.OrderStatus
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import kotlinx.coroutines.delay

class ChangeOrderStatusUseCase(
    private val repository: OrderRepository,
    private val removeOrderUseCase: RemoveOrderUseCase
) {
    suspend operator fun invoke(order: Order) {
        if (order.status != OrderStatus.Delivered) {
            val newStatus = when (order.status) {
                is OrderStatus.New -> OrderStatus.Preparing.value
                is OrderStatus.Preparing -> OrderStatus.Ready.value
                is OrderStatus.Ready -> OrderStatus.Delivered.value
                OrderStatus.Delivered -> OrderStatus.Delivered.value
            }
            repository.updateStatus(
                id = order.id!!,
                status = newStatus
            )
            if (newStatus == OrderStatus.Delivered.value) {
                delay(5000)
                removeOrderUseCase(order)
            }
        }
    }
}

