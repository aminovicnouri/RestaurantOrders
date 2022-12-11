package com.aminovic.restaurantorders.domain.use_case

data class OrderUseCases(
    val addOrderUseCase: AddOrderUseCase,
    val changeOrderStatusUseCase: ChangeOrderStatusUseCase,
    val getOrdersUseCase: GetOrdersUseCase,
    val getOrderUseCase: GetOrderUseCase,
    val removeOrderUseCase: RemoveOrderUseCase
)
