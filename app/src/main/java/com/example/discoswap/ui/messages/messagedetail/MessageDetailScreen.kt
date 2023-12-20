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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.discoswap.R
import com.example.discoswap.ui.messages.MessageDetailApiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetailScreen(
    onBack: () -> Unit,
    messageDetailViewModel: MessageDetailViewModel = viewModel(factory = MessageDetailViewModel.Factory)
) {

    when (val messageDetailApiState = messageDetailViewModel.messageDetailApiState) {
        is MessageDetailApiState.Loading -> {
            Text("Loading message details from api...")
        }

        is MessageDetailApiState.Error -> {
            Text("Error loading message details from api.")
        }

        is MessageDetailApiState.Success -> {
            val messageText = messageDetailApiState.message.text?.let { transformHTML(it) }
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
                    Text(text = messageDetailApiState.message.subject, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    Text(text = messageText!!, fontWeight = FontWeight.Medium, fontSize = 14.sp)
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
            if(it.text.contains("## Reply above this line ##"))
                it.text = it.text.split("## Reply above this line ##")[1]
            if(it.text.contains("You can view details on this order"))
                it.text = it.text.split("You can view details on this order")[0]
        }
    )
}