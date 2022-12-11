package com.aminovic.restaurantorders.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aminovic.restaurantorders.data.modal.Order
import com.aminovic.restaurantorders.ui.theme.LocalSpacing


@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    clickItem: () -> Unit,
    changeStatus: () -> Unit,
    order: Order
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { clickItem() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(5f)
            )
            .padding(all = spacing.spaceMedium),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        order.name?.let { Text(text = it) }
        Text(
            modifier = Modifier
                .clickable { changeStatus() }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(5f)
                )
                .padding(all = spacing.spaceSmall),
            text = order.status.value
        )
    }
}