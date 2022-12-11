package com.aminovic.restaurantorders.util


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

sealed class UiEvent {
    data class ShowSnackbar(val message: UiText) : UiEvent()
    data class ScrollToBottom(val position: Int) : UiEvent()
}