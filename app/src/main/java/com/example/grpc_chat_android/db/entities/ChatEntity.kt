package com.example.grpc_chat_android.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.grpc_chat_android.models.Chat

@Entity(indices = [
    Index(value = ["time","chatId","body"]),
])
data class ChatEntity(
        @PrimaryKey(autoGenerate = true)
        val id : Long,
        val body: String,
        val sender: String,
        val chatId : Long,
        val time : Long
){
    constructor(message: Chat.Message) : this(
        0,
        message.body,
        message.sender,
        0,
        message.sent
    )
}