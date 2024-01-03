package com.example.discoswap.ui.order.orderoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.order.Order
import com.example.discoswap.model.order.Status
import com.example.discoswap.ui.common.Tab
import com.example.discoswap.ui.common.TabView
import com.example.discoswap.ui.order.OrderApiState
import com.example.discoswap.ui.order.components.Orders

@Composable
fun OrderOverviewScreen(
    orderOverviewViewModel: OrderOverviewViewModel = viewModel(factory = OrderOverviewViewModel.Factory),
    onViewDetailClicked: (Order) -> Unit,
) {
    when (orderOverviewViewModel.orderApiState) {
        is OrderApiState.Loading -> {
            Text(stringResource(R.string.loading_orders))
        }

        is OrderApiState.Error -> {
            Text(stringResource(R.string.error_loading_orders))
        }

        is OrderApiState.Success -> {
            val items = orderOverviewViewModel.uiState.collectAsState().value.currentOrderList
            TabView(
                Tab(R.string.title_orders_all) {
                    Orders(
                        items,
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_orders_unpaid) {
                    Orders(
                        items.filter { it.status == Status.InvoiceSent },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_orders_paid) {
                    Orders(
                        items.filter { it.status == Status.PaymentReceived },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_orders_sent) {
                    Orders(
                        items.filter { it.status == Status.Shipped },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_orders_other) {
                    Orders(
                        items.filter { it.status != Status.Shipped && it.status != Status.PaymentReceived && it.status != Status.InvoiceSent },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
            )
        }
    }
}
