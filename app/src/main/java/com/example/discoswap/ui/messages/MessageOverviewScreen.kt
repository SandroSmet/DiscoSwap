package com.example.discoswap.ui.messages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.model.messages.Message

@Composable
fun MessageOverviewScreen(
    messageOverviewViewModel: MessageOverviewViewModel = viewModel(),
    onViewDetailClicked: (Message) -> Unit,
) {
    val messageOverviewState by messageOverviewViewModel.uiState.collectAsState()
    val items = messageOverviewState.currentMessageList
}
