@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.discoswap.ui.message.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.discoswap.model.message.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageListItem(
    message: Message,
    onViewDetailClicked: (Message) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        ListItem(
            modifier = Modifier.clickable
                {
                    onViewDetailClicked(message)
                },
            colors = ListItemDefaults.colors(
                containerColor = if(message.read) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primaryContainer,
            ),
            headlineText = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        message.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            },

            supportingText = {
                Text(message.subject, Modifier.padding(3.dp))
            },
        )
        Divider()
    }
}
