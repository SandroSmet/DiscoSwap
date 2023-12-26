package com.example.discoswap.ui.orders.orderoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.orders.Order
import com.example.discoswap.model.orders.Status
import com.example.discoswap.ui.common.Tab
import com.example.discoswap.ui.common.TabView
import com.example.discoswap.ui.messages.MessageApiState
import com.example.discoswap.ui.orders.OrderApiState
import com.example.discoswap.ui.orders.components.Orders

@Composable
fun OrderOverviewScreen(
    orderOverviewViewModel: OrderOverviewViewModel = viewModel(factory = OrderOverviewViewModel.Factory),
    onViewDetailClicked: (Order) -> Unit,
) {
    when (val orderApiState = orderOverviewViewModel.orderApiState) {
        is OrderApiState.Loading -> {
            Text("Loading orders from api...")
        }

        is OrderApiState.Error -> {
            Text("Error loading orders from api.")
        }

        is OrderApiState.Success -> {
            val items = orderApiState.orders
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
