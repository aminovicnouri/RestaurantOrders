package com.aminovic.restaurantorders.domain.use_case

import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.data.modal.OrderStatus
import com.aminovic.restaurantorders.domain.mapper.toOrderEntity
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import java.time.LocalDate

class AddOrderUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke() {
        repository.insertOrder(
            Order(
                status = OrderStatus.New,
                date = LocalDate.now()
            ).toOrderEntity()
        )
    }
}

