package com.aminovic.restaurantorders.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

@Database(
    entities = [OrderEntity::class],
    version = 1
)
abstract class OrderDatabase : RoomDatabase() {
    abstract val dao: OrderDao
}