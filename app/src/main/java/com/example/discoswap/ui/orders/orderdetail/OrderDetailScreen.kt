package com.example.discoswap.ui.orders.orderdetail

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.ui.messages.MessageDetailApiState
import com.example.discoswap.ui.messages.messagedetail.MessageDetailViewModel
import com.example.discoswap.ui.orders.OrderDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    onBack: () -> Unit,
    orderDetailViewModel: OrderDetailViewModel = viewModel(factory = OrderDetailViewModel.Factory)
) {

    when (val orderDetailApiState = orderDetailViewModel.orderDetailApiState) {
        is OrderDetailApiState.Loading -> {
            Text("Loading order details from api...")
        }

        is OrderDetailApiState.Error -> {
            Text("Error loading order details from api.")
        }

        is OrderDetailApiState.Success -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.message_username) + " " + orderDetailApiState.order.buyer)
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack, "Menu back")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth(),
                ) {
                    Text(text = orderDetailApiState.order.id, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    Text(text = orderDetailApiState.order.status.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }

}