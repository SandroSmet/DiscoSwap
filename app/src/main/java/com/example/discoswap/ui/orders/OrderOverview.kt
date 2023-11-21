package com.example.discoswap.ui.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.orders.Order
import com.example.discoswap.model.orders.Status
import com.example.discoswap.ui.common.Tab
import com.example.discoswap.ui.common.TabView
import com.example.discoswap.ui.orders.components.Orders

@Composable
fun OrderOverview(
    orderOverviewViewModel: OrderOverviewViewModel = viewModel(),
    onViewDetailClicked: (Order) -> Unit,
) {
    val orderOverviewState by orderOverviewViewModel.uiState.collectAsState()
    val items = orderOverviewState.currentOrderList

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
