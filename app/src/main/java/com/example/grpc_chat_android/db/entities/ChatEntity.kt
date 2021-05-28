package com.example.grpc_chat_android.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.grpc_chat_android.models.Chat

@Entity(
    indices =
    [
        Index(value = ["time", "chatId", "body", "from", "id"]),
        Index(value = ["chatId", "body", "time", "from", "id"])]
)
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val body: String,
    val from: String,
    val to: String,
    val chatId: String?,
    val time: Long
) {
    constructor(message: Chat.Message, chatId: String, timeMillis: Long) : this(
        message.id,
        message.body,
        message.from,
        message.to,
        chatId,
        timeMillis
    )
}
