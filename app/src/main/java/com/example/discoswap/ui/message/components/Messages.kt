package com.example.discoswap.ui.message.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.discoswap.model.message.Message

@Composable
fun Messages(
    messages: List<Message>,
    onViewDetailClicked: (Message) -> Unit,
) {
    if (messages.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
        ) {
            items(messages) { message ->
                MessageListItem(
                    message,
                    onViewDetailClicked = { onViewDetailClicked(message) },
                )
            }
        }
    }
}
