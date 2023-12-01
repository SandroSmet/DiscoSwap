package com.example.discoswap.ui.messages.messageoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.model.messages.Message
import com.example.discoswap.model.messages.Type
import com.example.discoswap.ui.common.Tab
import com.example.discoswap.ui.common.TabView
import com.example.discoswap.ui.messages.MessageApiState
import com.example.discoswap.ui.messages.components.Messages

@Composable
fun MessageOverview(
    messageOverviewViewModel: MessageOverviewViewModel = hiltViewModel(),
    onViewDetailClicked: (Message) -> Unit,
) {
    val messageOverviewState by messageOverviewViewModel.uiState.collectAsState()

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
