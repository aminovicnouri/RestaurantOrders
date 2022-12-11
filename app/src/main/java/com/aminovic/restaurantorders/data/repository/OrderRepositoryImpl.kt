package com.aminovic.restaurantorders.data.repository

import com.aminovic.restaurantorders.data.local.OrderDao
import com.aminovic.restaurantorders.data.local.OrderEntity
import com.aminovic.restaurantorders.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImpl(
    private val dao: OrderDao
) : OrderRepository {

    override suspend fun insertOrder(order: OrderEntity) {
        dao.insertOrder(order)
    }

    override suspend fun deleteOrder(order: OrderEntity) {
        dao.deleteOrder(order)
    }

    override suspend fun updateStatus(id: Int, status: String) {
        dao.updateStatus(id, status)
    }

    override suspend fun getOrder(id: Int): OrderEntity {
        return dao.getOrder(id)
    }

    override fun getOrders(): Flow<List<OrderEntity>> {
        return dao.getOrders()
    }
}