package com.karry.data.models

data class RecentChat(
    val id: String,
    val receiverId: String,
    val receiverName: String,
    val lastMessage: String,
    val timestamp: Long
)
