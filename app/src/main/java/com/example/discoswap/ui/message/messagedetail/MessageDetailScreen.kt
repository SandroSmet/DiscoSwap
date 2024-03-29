package com.example.discoswap.ui.message.messagedetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.ui.message.MessageDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetailScreen(
    onBack: () -> Unit,
    messageDetailViewModel: MessageDetailViewModel = viewModel(factory = MessageDetailViewModel.Factory)
) {

    when (messageDetailViewModel.messageDetailApiState) {
        is MessageDetailApiState.Loading -> {
            Text(stringResource(R.string.loading_message_details))
        }

        is MessageDetailApiState.Error -> {
            Text(stringResource(R.string.error_loading_message_details))
        }

        is MessageDetailApiState.Success -> {
            val message = messageDetailViewModel.uiState.collectAsState().value.message!!
            val messageText = message.text?.let { transformHTML(it) }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.message_detail_from, message.name))
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(Icons.Filled.ArrowBack, stringResource(R.string.menu_back))
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .testTag("messageDetail"),
                ) {
                    Text(text = message.subject, style = MaterialTheme.typography.titleLarge)
                    if (messageText != null) {
                        Text(text = messageText, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }

}

fun transformHTML(html: String): String {
    var transformedText: String = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()

    if(transformedText.contains("￼ ￼ ￼ ￼ "))
        transformedText = transformedText.split("￼ ￼ ￼ ￼ ")[1]
    if(transformedText.contains("; } }"))
        transformedText = transformedText.split("; } }")[1]
    if(transformedText.contains("￼"))
        transformedText = transformedText.replace(Regex("￼"), "")
    if(transformedText.contains("## Reply above this line ##"))
        transformedText = transformedText.split("## Reply above this line ##")[1]
    if(transformedText.contains("You can view details on this order"))
        transformedText = transformedText.split("You can view details on this order")[0]

    return transformedText
}