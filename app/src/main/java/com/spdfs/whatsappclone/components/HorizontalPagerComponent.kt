package com.spdfs.whatsappclone.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.spdfs.whatsappclone.data.chatListItems
import com.spdfs.whatsappclone.screens.ShowUserList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerComponent(context: Context, viewPagerState: PagerState) {

    HorizontalPager(
        state = viewPagerState,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) { page ->
        when (page) {
            0 -> {
                ShowUserList(context = context, chatList = chatListItems)
            }
            // 1 -> //TODO
            // 2 -> //TODO
        }
    }
}

