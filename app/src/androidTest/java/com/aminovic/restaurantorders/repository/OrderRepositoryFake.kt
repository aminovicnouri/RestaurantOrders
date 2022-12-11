package com.aminovic.restaurantorders.repository

import com.aminovic.restaurantorders.data.local.OrderEntity
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.random.Random

//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

class OrderRepositoryFake : OrderRepository {

    var orders = mutableListOf<OrderEntity>()

    private val getOrdersFlow =
        MutableSharedFlow<List<OrderEntity>>(replay = 1)


    override suspend fun insertOrder(order: OrderEntity) {
        orders.add(order.copy(id = Random.nextInt()))
        getOrdersFlow.emit(orders)
    }

    override suspend fun deleteOrder(order: OrderEntity) {
        orders.remove(order)
        getOrdersFlow.emit(orders)
    }

    override suspend fun updateStatus(id: Int, status: String) {
        orders
            .find { it.id == id }?.let {
                orders[orders.indexOf(it)] = it.copy(
                    status = status
                )
            }
        getOrdersFlow.emit(orders)
    }

    override suspend fun getOrder(id: Int): OrderEntity {
        return orders.find { it.id == id }!!
    }

    override fun getOrders(): Flow<List<OrderEntity>> {
        return getOrdersFlow
    }
}
