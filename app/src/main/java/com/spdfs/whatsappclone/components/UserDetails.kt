package com.spdfs.whatsappclone.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spdfs.whatsappclone.R
import com.spdfs.whatsappclone.model.ChatData
import com.spdfs.whatsappclone.model.MessageDeliveryStatus
import com.spdfs.whatsappclone.model.MessageType

@Composable
fun UserDetails(
    chatData: ChatData
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        MessageHeader(chatData)
        SubMessage(chatData)
    }
}

@Composable
private fun MessageHeader(chatData: ChatData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = chatData.userName,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                color = if (isSystemInDarkTheme()) White else Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
        )
        Text(
            text = chatData.timeStamp,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) White else Black,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
            )
        )
    }
}

@Composable
private fun SubMessage(chatData: ChatData) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (chatData.lastMessageSentByMe) {
            DeliveryStatusIcon(chatData.deliveryStatus)
        }
        if (chatData.messageType != MessageType.TEXT) {
            MessageMediaIcon(chatData.messageType)
        }

        val message =
            subMessage(chatData.lastMessageSentByMe, chatData.messageType, chatData.message)
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = if (chatData.lastMessageSentByMe) {
                        2.dp
                    } else if (chatData.messageType != MessageType.TEXT) {
                        2.dp
                    } else {
                        0.dp
                    },
                ),
            text = message,
            style = TextStyle(
                color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MessageMediaIcon(messageType: MessageType) {
    if (messageType != MessageType.TEXT || messageType != MessageType.REACTION) {
        val icon = when (messageType) {
            MessageType.TEXT -> TODO()
            MessageType.VIDEO -> R.drawable.file_video_icon
            MessageType.IMAGE -> R.drawable.file_image_icon
            MessageType.AUDIO -> R.drawable.file_audio_icon
            MessageType.REACTION -> TODO()
        }
        Image(
            modifier = Modifier.size(18.dp),
            painter = painterResource(id = icon),
            contentDescription = "media type",
            colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(White) else ColorFilter.tint(Black)
        )
    }
}

@Composable
private fun DeliveryStatusIcon(deliveryStatus: MessageDeliveryStatus?) {
    val deliveryIcon = when (deliveryStatus) {
        MessageDeliveryStatus.NOT_SENT -> R.drawable.single_check_icon
        MessageDeliveryStatus.PENDING -> R.drawable.check_double_fill_icon
        MessageDeliveryStatus.DELIVERED, MessageDeliveryStatus.READ -> R.drawable.check_double_fill_icon
        else -> null
    }
    Image(
        modifier = Modifier.size(18.dp),
        painter = painterResource(id = deliveryIcon!!),
        contentDescription = "delivery status",
        colorFilter = ColorFilter.tint(
            if (deliveryStatus == MessageDeliveryStatus.READ) {
                Blue
            } else {
                LightGray
            }
        )
    )
}

private fun subMessage(
    messageSentByMe: Boolean,
    messageType: MessageType,
    message: String?
): String {
    return when (messageType) {
        MessageType.TEXT -> message!!
        MessageType.VIDEO -> "Video"
        MessageType.IMAGE -> "Photo"
        MessageType.AUDIO -> "Audio"
        MessageType.REACTION -> "${if (messageSentByMe) "You " else ""}reacted to a message"
    }
}