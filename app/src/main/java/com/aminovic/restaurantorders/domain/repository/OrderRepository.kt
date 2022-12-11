package com.aminovic.restaurantorders.domain.repository

import com.aminovic.restaurantorders.data.local.OrderEntity
import kotlinx.coroutines.flow.Flow


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

interface OrderRepository {
    suspend fun insertOrder(order: OrderEntity)

    suspend fun deleteOrder(order: OrderEntity)

    suspend fun updateStatus(id: Int, status: String)

    suspend fun getOrder(id: Int): OrderEntity

    fun getOrders(): Flow<List<OrderEntity>>
}