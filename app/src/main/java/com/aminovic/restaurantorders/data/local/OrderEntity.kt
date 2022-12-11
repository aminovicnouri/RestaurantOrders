package com.aminovic.restaurantorders.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey val id: Int? = null,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val status: String
)
