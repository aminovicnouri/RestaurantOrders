package com.aminovic.restaurantorders.presentation.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.aminovic.restaurantorders.R
import com.aminovic.restaurantorders.ui.theme.LocalSpacing
import java.time.format.DateTimeFormatter

@Composable
fun DetailsScreen(
    orderId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
) {

    val spacing = LocalSpacing.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.getOrder(orderId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = spacing.spaceMedium,
                horizontal = spacing.spaceMedium
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
        Text(
            text = stringResource(id = R.string.order_details),
            style = MaterialTheme.typography.h4
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                Text(
                    text = stringResource(id = R.string.loading),
                    style = MaterialTheme.typography.h4
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = state.order!!.name!!,
                        style = MaterialTheme.typography.h4
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceLarge))
                    Text(
                        text = state.order.date.format(DateTimeFormatter.ofPattern("dd LLLL yyyy")),
                        style = MaterialTheme.typography.h4
                    )
                }
            }
        }
    }
}