package com.aminovic.restaurantorders.data.modal

sealed class OrderStatus(val value: String) {
    object New : OrderStatus("new")
    object Preparing : OrderStatus("preparing")
    object Ready : OrderStatus("ready")
    object Delivered : OrderStatus("delivered")

    companion object {
        fun getOrderStatusFromValue(value: String): OrderStatus {
            return when (value) {
                "new" -> New
                "preparing" -> Preparing
                "ready" -> Ready
                else -> Delivered
            }
        }
    }
}

