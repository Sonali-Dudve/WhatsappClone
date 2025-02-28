package com.spdfs.whatsappclone.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spdfs.whatsappclone.model.TabData
import com.spdfs.whatsappclone.model.Tabs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabBar(
    initialIndex: Int = 0,
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit,
) {

    val homeTabs = listOf(
        TabData(Tabs.CHAT), TabData(Tabs.MESSAGE_STATUS, 1), TabData(Tabs.CALLS, 5)
    )

    var selectedPage by remember {
        mutableIntStateOf(initialIndex)
    }

    var isTabClicked by remember {
        mutableStateOf(false)
    }

    var statusIndicatorVisible by remember {
        mutableStateOf(true)
    }

    var callIndicatorVisible by remember {
        mutableStateOf(true)
    }


    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = selectedPage,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedPage]),
                height = 2.5.dp,
                color = Green
            )
        }
    ) {
        homeTabs.forEachIndexed { index, tabData ->

            Tab(
                selected = index == selectedPage,
                onClick = {
                    selectedPage = index
                    onTabSelected(selectedPage)
                    isTabClicked = true
                    if (statusIndicatorVisible && selectedPage == 1) {
                        statusIndicatorVisible = false
                    }

                    if (callIndicatorVisible && selectedPage == 2) {
                        callIndicatorVisible = false
                    }
                },
                modifier = Modifier.height(38.dp),
                selectedContentColor = if (isSystemInDarkTheme()) Green else Green,
                unselectedContentColor = if (isSystemInDarkTheme()) Gray else Gray,
            ) {
                tabData.unreadCount?.let { count ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = tabData.tab.value, style = TextStyle(
                                fontSize = 16.sp,
                            )
                        )
                        if (tabData.tab == Tabs.MESSAGE_STATUS) {
                            AnimatedVisibility(visible = statusIndicatorVisible) {
                                TabIndicator()
                            }
                        } else {
                            AnimatedVisibility(visible = callIndicatorVisible) {
                                UnreadCountIndicator(count)
                            }
                        }
                    }
                } ?: Text(
                    text = tabData.tab.value,
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )
            }
        }
    }
}


@Composable
fun TabIndicator() {
    Box(
        modifier = Modifier
            .padding(start = 4.dp)
            .size(5.dp)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Gray
                } else {
                    Green
                },
            )
    )
}

@Composable
fun UnreadCountIndicator(count: Int) {
    Text(
        text = count.toString(),
        style = TextStyle(
            fontSize = 12.sp,
            color = if (isSystemInDarkTheme()) Gray else Blue,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .padding(4.dp)
            .size(16.dp)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Gray
                } else {
                    Green
                },
            ),
    )
}
