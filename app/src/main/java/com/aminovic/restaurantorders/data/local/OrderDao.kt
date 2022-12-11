package com.aminovic.restaurantorders.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderEntity: OrderEntity)

    @Delete
    suspend fun deleteOrder(orderEntity: OrderEntity)

    @Query("UPDATE orderentity SET status=:status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("SELECT * FROM orderentity WHERE id = :id")
    suspend fun getOrder(id: Int): OrderEntity

    @Query("SELECT * FROM orderentity")
    fun getOrders(): Flow<List<OrderEntity>>
}