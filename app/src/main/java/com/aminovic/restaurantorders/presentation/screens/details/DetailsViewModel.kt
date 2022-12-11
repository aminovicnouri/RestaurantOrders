package com.aminovic.restaurantorders.presentation.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminovic.restaurantorders.domain.use_case.GetOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


//
// Created by Mohamed El Amine Nouri on 11/12/2022.
//

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase
) : ViewModel() {

    var state by mutableStateOf(DetailsState())
        private set

    fun getOrder(id: Int) {
        viewModelScope.launch {
            getOrderUseCase(id).let { order ->
                delay(500)
                state = state.copy(
                    order = order,
                    isLoading = false
                )
            }
        }
    }
}