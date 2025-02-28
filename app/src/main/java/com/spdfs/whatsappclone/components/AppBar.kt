package com.spdfs.whatsappclone.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spdfs.whatsappclone.ui.theme.Purple40

@Composable
fun AppBar() {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Whatsapp",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSystemInDarkTheme()) Purple40 else Black
            )
        )
        Spacer(modifier = Modifier.width(32.dp))
    }
}
