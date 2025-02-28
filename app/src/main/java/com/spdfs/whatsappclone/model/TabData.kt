package com.spdfs.whatsappclone.model

data class TabData(
    val tab: Tabs,
    val unreadCount: Int? = null,
)

enum class Tabs(val value: String) {
    CHAT("Chats"),
    MESSAGE_STATUS("Status"),
    CALLS("Calls");
}