package com.spdfs.whatsappclone.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spdfs.whatsappclone.components.AppBar
import com.spdfs.whatsappclone.components.HorizontalPagerComponent
import com.spdfs.whatsappclone.components.TabBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListScreen(context: Context) {

    val viewPagerState = rememberPagerState(0, 0F) { 3 }
    val scope = rememberCoroutineScope()

    Column {
        Spacer(modifier = Modifier.height(20.dp))
        AppBar()
        Spacer(modifier = Modifier.height(20.dp))
        TabBar(
            initialIndex = 0,
            pagerState = viewPagerState
        ) { selectedTab ->
            scope.launch {
                viewPagerState.animateScrollToPage(selectedTab)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        HorizontalPagerComponent(
            context = context,
            viewPagerState = viewPagerState
        )
    }
}