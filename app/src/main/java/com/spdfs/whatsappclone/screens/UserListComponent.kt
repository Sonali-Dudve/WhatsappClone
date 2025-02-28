package com.spdfs.whatsappclone.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spdfs.whatsappclone.ChatScreen
import com.spdfs.whatsappclone.components.UserDetails
import com.spdfs.whatsappclone.components.UserImage
import com.spdfs.whatsappclone.model.ChatData

@Composable
fun ShowUserList(context: Context, chatList: List<ChatData>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(
            items = chatList,
        ) { chatData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        println("List Item Clicked: $chatData")
                        val intent = Intent(context, ChatScreen::class.java)
                        intent.putExtra("user_name", chatData.userName)
                        intent.putExtra("user_image", chatData.userImage)
                        context.startActivity(intent)
                    }
            ) {
                UserListItem(chatData)
            }
        }

        item {
            Spacer(modifier = Modifier.height(75.dp))
        }
    }
}

@Composable
fun UserListItem(chatData: ChatData) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserImage(chatData.userImage)
        UserDetails(chatData)
    }
}
