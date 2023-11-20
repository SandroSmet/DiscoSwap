package com.example.discoswap.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

class Tab(
    @StringRes val title: Int,
    val content: @Composable () -> Unit,
)

@Composable
fun TabView(vararg tabs: Tab) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(stringResource(tab.title)) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                )
            }
        }
        tabs[selectedTabIndex].content()
    }
}
