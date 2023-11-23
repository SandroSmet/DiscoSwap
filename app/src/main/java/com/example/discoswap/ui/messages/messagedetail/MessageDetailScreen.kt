package com.example.discoswap.ui.messages.messagedetail

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.discoswap.R
import com.example.discoswap.ui.messages.MessageDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetailScreen(onBack: () -> Unit, viewModel: MessageDetailViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val messageDetailState by viewModel.uiState.collectAsState()
    val message = messageDetailState.message

    when (val messageDetailApiState = viewModel.messageDetailApiState) {
        is MessageDetailApiState.Loading -> {
            Text("Loading messages from api...")
        }

        is MessageDetailApiState.Error -> {
            Text("Error loading messages from api.")
        }

        is MessageDetailApiState.Success -> {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(R.string.message_username) + " " + messageDetailApiState.message.name)
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
                    messageDetailApiState.message.text?.let { HtmlText(it) }
                }
            }
        }
    }

}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val textView = TextView(context)
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView
                  },
        update = { it.text =
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
            if(it.text.contains("￼ ￼ ￼ ￼ "))
                it.text = it.text.split("￼ ￼ ￼ ￼ ")[1]
            if(it.text.contains("; } }"))
                it.text = it.text.split("; } }")[1]

            if(it.text.contains("￼"))
                it.text = it.text.replace(Regex("￼"), "")
        }
    )
}