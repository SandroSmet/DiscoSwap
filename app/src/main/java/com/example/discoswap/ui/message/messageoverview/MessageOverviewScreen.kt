package com.example.discoswap.ui.message.messageoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.message.Message
import com.example.discoswap.model.message.Type
import com.example.discoswap.ui.common.Tab
import com.example.discoswap.ui.common.TabView
import com.example.discoswap.ui.message.MessageApiState
import com.example.discoswap.ui.message.components.Messages

@Composable
fun MessageOverviewScreen(
    messageOverviewViewModel: MessageOverviewViewModel = viewModel(factory = MessageOverviewViewModel.Factory),
    onViewDetailClicked: (Message) -> Unit,
) {
    when (val messageApiState = messageOverviewViewModel.messageApiState) {
        is MessageApiState.Loading -> {
            Text("Loading messages from api...")
        }
        is MessageApiState.Error -> {
            Text("Error loading messages from api.")
        }
        is MessageApiState.Success -> {
            val items = messageApiState.messages
            TabView(
                Tab(R.string.title_messages_all) {
                    Messages(
                        items,
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_messages_order) {
                    Messages(
                        items.filter { it.type == Type.Order },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_messages_user) {
                    Messages(
                        items.filter { it.type == Type.User },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
                Tab(R.string.title_messages_other) {
                    Messages(
                        items.filter { it.type == Type.Other },
                        onViewDetailClicked = onViewDetailClicked,
                    )
                },
            )
        }
    }
}
