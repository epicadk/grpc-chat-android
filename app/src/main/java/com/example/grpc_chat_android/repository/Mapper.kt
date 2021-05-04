package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat

object Mapper {
    fun toProto(entity: ChatEntity): Chat.Message {
        return Chat.Message.newBuilder()
            .apply {
                body = entity.body
                sender = entity.sender
                sent = entity.time
            }
            .build()
    }

    fun fromProto(message: Chat.Message): ChatEntity {
        return ChatEntity(0, message.body, message.sender, message.receiver, 0, message.sent)
    }
}
