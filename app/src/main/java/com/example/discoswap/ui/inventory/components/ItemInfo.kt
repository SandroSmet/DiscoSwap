package com.example.discoswap.ui.inventory.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ItemInfo(
    info: String,
    modifier: Modifier,
) {
    Text(
        text = info,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        modifier = modifier,
    )
}