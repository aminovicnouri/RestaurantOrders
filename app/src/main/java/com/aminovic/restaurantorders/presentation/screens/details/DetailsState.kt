package com.aminovic.restaurantorders.presentation.screens.details

import com.aminovic.restaurantorders.data.modal.Order


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

data class DetailsState(
    val order: Order? = null,
    val isLoading: Boolean = true
)